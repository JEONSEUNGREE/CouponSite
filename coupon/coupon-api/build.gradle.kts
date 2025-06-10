dependencies {
    implementation(project(":coupon-core")) // 의존성만 추가한 부분이고 application에 클래스에 컴포넌트 스캔을 위해 import 필요
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}