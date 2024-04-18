import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class ArchitectureTest {
     private val basePackageName = "net.bk24.macro"

     @Test
     fun `인프라 계층은 코어에 의해서만 접근되어야 한다`() {
          val importedClasses: JavaClasses =
               ClassFileImporter().importPackages(
                    "$basePackageName.infra..",
               )

          noClasses()
               .that().resideInAPackage("$basePackageName.infra..")
               .should().onlyAccessClassesThat().resideInAPackage(
                    "$basePackageName.core..",
               )
               .check(importedClasses)
     }

     @Test
     fun `웹 계층은 코어에 의해서만 접근되어야 한다`() {
          val importedClasses: JavaClasses =
               ClassFileImporter().importPackages(
                    "$basePackageName.web..",
               )

          noClasses()
               .that().resideInAPackage("$basePackageName.web..")
               .should().onlyAccessClassesThat().resideInAPackage(
                    "$basePackageName.core..",
               )
               .check(importedClasses)
     }

     @Test
     fun `웹 계층은 인프라에 접근해서는 안된다`() {
          val importedClasses: JavaClasses =
               ClassFileImporter().importPackages(
                    "$basePackageName.web..",
               )

          noClasses()
               .that().resideInAPackage("$basePackageName.web..")
               .should().onlyAccessClassesThat().resideInAPackage(
                    "$basePackageName.infra..",
               )
               .check(importedClasses)
     }

     @Test
     fun `인프라는 웹에 접근해서는 안된다()`() {
          val importedClasses: JavaClasses =
               ClassFileImporter().importPackages(
                    "$basePackageName.infra..",
               )

          noClasses()
               .that().resideInAPackage("$basePackageName.infra..")
               .should().onlyAccessClassesThat().resideInAPackage("$basePackageName.web..")
               .check(importedClasses)
     }
}
