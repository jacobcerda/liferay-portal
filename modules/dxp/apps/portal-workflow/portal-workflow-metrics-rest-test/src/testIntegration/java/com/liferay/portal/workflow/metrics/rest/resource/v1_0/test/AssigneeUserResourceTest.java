/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUserBulkSelection;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class AssigneeUserResourceTest extends BaseAssigneeUserResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteSLATaskResults();
		_deleteTasks();
		_deleteTasks();
	}

	@Override
	@Test
	public void testPostProcessAssigneeUsersPage() throws Exception {
		AssigneeUser assigneeUser1 = randomAssigneeUser();

		assigneeUser1.setOnTimeTaskCount(0L);
		assigneeUser1.setOverdueTaskCount(3L);
		assigneeUser1.setTaskCount(3L);

		Instance instance1 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance1, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		_addTask(
			assigneeUser1.getId(), () -> instance1, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Instance instance2 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance2, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Role siteAdministrationRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		AssigneeUser assigneeUser2 = _randomAssigneeUser(
			siteAdministrationRole);

		assigneeUser2.setOnTimeTaskCount(1L);
		assigneeUser2.setOverdueTaskCount(1L);
		assigneeUser2.setTaskCount(2L);

		_addTask(
			assigneeUser2.getId(), () -> instance1, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		_addTask(
			assigneeUser2.getId(), () -> instance2, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "submit";
					name = "submit";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Page<AssigneeUser> page =
			assigneeUserResource.postProcessAssigneeUsersPage(
				_process.getId(), Pagination.of(1, 10), "taskCount:asc",
				new AssigneeUserBulkSelection() {
					{
						completed = false;
						taskKeys = new String[] {"update"};
					}
				});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 2L;
						taskCount = 2L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeUserBulkSelection() {
				{
					completed = false;
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				},
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeUserBulkSelection() {
				{
					completed = false;
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeUserBulkSelection() {
				{
					completed = false;
					keywords = assigneeUser2.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeUserBulkSelection() {
				{
					completed = false;
					keywords = assigneeUser1.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(0, page.getTotalCount());

		AssigneeUser assigneeUser3 = randomAssigneeUser();

		assigneeUser3.setOnTimeTaskCount(0L);
		assigneeUser3.setOverdueTaskCount(0L);
		assigneeUser3.setTaskCount(1L);

		_addTask(
			assigneeUser3.getId(), () -> instance1, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeUserBulkSelection() {
				{
					completed = false;
				}
			});

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(assigneeUser1, assigneeUser2, assigneeUser3),
			(List<AssigneeUser>)page.getItems());

		Instance instance3 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), true, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance3, _process.getId(),
			"COMPLETED",
			new Task() {
				{
					durationAvg = 1000L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new Task() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		_addTask(
			assigneeUser2.getId(), () -> instance3, _process.getId(),
			"COMPLETED",
			new Task() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new Task() {
				{
					durationAvg = 4000L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeUserResource.postProcessAssigneeUsersPage(
			_process.getId(), Pagination.of(1, 10), "durationTaskAvg:asc",
			new AssigneeUserBulkSelection() {
				{
					completed = true;
					dateEnd = RandomTestUtil.nextDate();
					dateStart = DateUtils.addMinutes(
						RandomTestUtil.nextDate(), -2);
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 1500L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				},
				new AssigneeUser() {
					{
						durationTaskAvg = 3000L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				}),
			(List<AssigneeUser>)page.getItems());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"durationTaskAvg", "id", "name", "onTimeTaskCount",
			"overdueTaskCount", "taskCount"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"durationTaskAvg"};
	}

	@Override
	protected AssigneeUser randomAssigneeUser() throws Exception {
		User user = UserTestUtil.addUser();

		return new AssigneeUser() {
			{
				durationTaskAvg = 0L;
				id = user.getUserId();
				image = user.getPortraitURL(
					new ThemeDisplay() {
						{
							setPathImage(_portal.getPathImage());
						}
					});
				name = user.getFullName();
				onTimeTaskCount = 1L;
				overdueTaskCount = 0L;
				taskCount = 1L;
			}
		};
	}

	private void _addRoleUser(Role role, long userId) throws Exception {
		_userLocalService.addRoleUser(role.getRoleId(), userId);

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {userId}, TestPropsValues.getGroupId(),
			role.getRoleId());
	}

	private void _addTask(
			long assigneeId,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, String status, Task... tasks)
		throws Exception {

		for (Task task : tasks) {
			_workflowMetricsRESTTestHelper.addTask(
				assigneeId, testGroup.getCompanyId(), instanceSupplier,
				processId, status, task, "1.0");
		}
	}

	private void _addTask(
			long assigneeId,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, Task... tasks)
		throws Exception {

		_addTask(assigneeId, instanceSupplier, processId, "RUNNING", tasks);
	}

	private void _deleteSLATaskResults() throws Exception {
		_workflowMetricsRESTTestHelper.deleteSLATaskResults(
			testGroup.getCompanyId(), _process.getId());
	}

	private void _deleteTasks() throws Exception {
		_workflowMetricsRESTTestHelper.deleteTasks(
			testGroup.getCompanyId(), _process.getId());
	}

	private AssigneeUser _randomAssigneeUser(Role role) throws Exception {
		AssigneeUser assigneeUser = randomAssigneeUser();

		_addRoleUser(role, assigneeUser.getId());

		return assigneeUser;
	}

	@Inject
	private Portal _portal;

	private Process _process;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}