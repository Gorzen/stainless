
lazy val root = (project in file("."))
	.settings(
		javaOptions += "-Xss1G",
		javaOptions += "-Xms2g",
		javaOptions += "-Xmx14g",
		fork in run := true,
		unmanagedSourceDirectories in Compile += baseDirectory.value / "library"
	)
