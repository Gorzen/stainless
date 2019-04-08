
lazy val root = (project in file("."))
	.settings(
		javaOptions += "-Xss1G",
		fork in run := true,
		unmanagedSourceDirectories in Compile += baseDirectory.value / "library"
	)
