val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.redisson:redisson-spring-boot-starter:3.16.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
