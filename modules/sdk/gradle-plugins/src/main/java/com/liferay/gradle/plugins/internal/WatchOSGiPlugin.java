/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.gradle.plugins.internal;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.internal.util.IncludeResourceCompileIncludeInstruction;
import com.liferay.gradle.plugins.tasks.ExecuteBndTask;
import com.liferay.gradle.plugins.tasks.WatchTask;
import com.liferay.gradle.plugins.util.BndUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class WatchOSGiPlugin implements Plugin<Project> {

	public static final String BUILD_BUNDLE_DIR_TASK_NAME = "buildBundleDir";

	public static final Plugin<Project> INSTANCE = new WatchOSGiPlugin();

	public static final String JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME =
		"jarCompileIncludeFragment";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(Project project) {

		// Tasks

		TaskProvider<Sync> buildBundleDirTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_BUNDLE_DIR_TASK_NAME, Sync.class);
		TaskProvider<ExecuteBndTask> jarCompileIncludeFragmentTaskProvider =
			GradleUtil.addTaskProvider(
				project, JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME,
				ExecuteBndTask.class);
		TaskProvider<WatchTask> watchTaskProvider = GradleUtil.addTaskProvider(
			project, WATCH_TASK_NAME, WatchTask.class);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		_configureTaskBuildBundleDirProvider(
			project, buildBundleDirTaskProvider, jarTaskProvider);
		_configureTaskJarCompileIncludeFragmentProvider(
			project, jarCompileIncludeFragmentTaskProvider);
		_configureTaskWatchProvider(
			buildBundleDirTaskProvider, jarCompileIncludeFragmentTaskProvider,
			watchTaskProvider);
	}

	private WatchOSGiPlugin() {
	}

	private void _configureTaskBuildBundleDirProvider(
		final Project project, TaskProvider<Sync> buildBundleDirTaskProvider,
		final TaskProvider<Jar> jarTaskProvider) {

		buildBundleDirTaskProvider.configure(
			new Action<Sync>() {

				@Override
				public void execute(Sync buildBundleDirTask) {
					buildBundleDirTask.dependsOn(jarTaskProvider);

					buildBundleDirTask.from(
						new Callable<FileTree>() {

							@Override
							public FileTree call() throws Exception {
								Jar jarTask = jarTaskProvider.get();

								return project.zipTree(
									jarTask.getArchivePath());
							}

						});

					buildBundleDirTask.into(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(),
									BUILD_BUNDLE_DIR_TASK_NAME);
							}

						});

					buildBundleDirTask.setDescription(
						"Unzips the project's JAR file into a temporary " +
							"directory.");
				}

			});
	}

	private void _configureTaskJarCompileIncludeFragmentProvider(
		final Project project,
		TaskProvider<ExecuteBndTask> jarCompileIncludeFragmentTaskProvider) {

		jarCompileIncludeFragmentTaskProvider.configure(
			new Action<ExecuteBndTask>() {

				@Override
				public void execute(
					ExecuteBndTask jarCompileIncludeFragmentTask) {

					jarCompileIncludeFragmentTask.property(
						Constants.BUNDLE_NAME,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								String instruction = BndUtil.getInstruction(
									project, Constants.BUNDLE_NAME);

								return instruction + " Libs";
							}

						});

					jarCompileIncludeFragmentTask.property(
						Constants.BUNDLE_SYMBOLICNAME,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								String instruction = BndUtil.getInstruction(
									project, Constants.BUNDLE_SYMBOLICNAME);

								return instruction + ".libs";
							}

						});

					jarCompileIncludeFragmentTask.property(
						Constants.BUNDLE_VERSION, "1.0.0");

					jarCompileIncludeFragmentTask.property(
						Constants.FRAGMENT_HOST,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return BndUtil.getInstruction(
									project, Constants.BUNDLE_SYMBOLICNAME);
							}

						});

					jarCompileIncludeFragmentTask.property(
						Constants.INCLUDERESOURCE,
						new IncludeResourceCompileIncludeInstruction(
							new Callable<Iterable<File>>() {

								@Override
								public Iterable<File> call() throws Exception {
									return GradleUtil.getConfiguration(
										project,
										LiferayOSGiPlugin.
											COMPILE_INCLUDE_CONFIGURATION_NAME);
								}

							},
							new Callable<Boolean>() {

								@Override
								public Boolean call() throws Exception {
									return Boolean.FALSE;
								}

							}));

					jarCompileIncludeFragmentTask.onlyIf(
						new Spec<Task>() {

							@Override
							public boolean isSatisfiedBy(Task task) {
								Configuration configuration =
									GradleUtil.getConfiguration(
										project,
										LiferayOSGiPlugin.
											COMPILE_INCLUDE_CONFIGURATION_NAME);

								return !configuration.isEmpty();
							}

						});

					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					jarCompileIncludeFragmentTask.setClasspath(
						sourceSet.getCompileClasspath());

					jarCompileIncludeFragmentTask.setDescription(
						"Generates an OSGi fragment containing all " +
							"dependencies of " +
								LiferayOSGiPlugin.
									COMPILE_INCLUDE_CONFIGURATION_NAME + ".");
					jarCompileIncludeFragmentTask.setGroup(
						BasePlugin.BUILD_GROUP);

					jarCompileIncludeFragmentTask.setOutputFile(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(),
									project.getName() + "-libs." +
										Jar.DEFAULT_EXTENSION);
							}

						});

					jarCompileIncludeFragmentTask.setResourceDirs(
						project.files());
					jarCompileIncludeFragmentTask.setSourceDirs(
						project.files());
				}

			});
	}

	private void _configureTaskWatchProvider(
		final TaskProvider<Sync> buildBundleDirTaskProvider,
		final TaskProvider<ExecuteBndTask>
			jarCompileIncludeFragmentTaskProvider,
		TaskProvider<WatchTask> watchTaskProvider) {

		watchTaskProvider.configure(
			new Action<WatchTask>() {

				@Override
				public void execute(WatchTask watchTask) {
					Sync buildBundleDirTask = buildBundleDirTaskProvider.get();

					watchTask.dependsOn(buildBundleDirTask);

					watchTask.setBundleDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return buildBundleDirTask.getDestinationDir();
							}

						});

					watchTask.setBundleSymbolicName(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								File manifestFile = new File(
									buildBundleDirTask.getDestinationDir(),
									"META-INF/MANIFEST.MF");

								if (manifestFile.exists()) {
									try (FileInputStream fileInputStream =
											new FileInputStream(manifestFile)) {

										Manifest manifest = new Manifest(
											fileInputStream);

										Attributes attributes =
											manifest.getMainAttributes();

										return attributes.getValue(
											"Bundle-SymbolicName");
									}
								}

								return null;
							}

						});

					watchTask.setDescription(
						"Continuously redeploys the project's OSGi bundle.");
					watchTask.setGroup(BasePlugin.BUILD_GROUP);

					ExecuteBndTask jarCompileIncludeFragmentTask =
						jarCompileIncludeFragmentTaskProvider.get();

					TaskOutputs taskOutputs =
						jarCompileIncludeFragmentTask.getOutputs();

					watchTask.setFragments(taskOutputs.getFiles());
				}

			});
	}

}