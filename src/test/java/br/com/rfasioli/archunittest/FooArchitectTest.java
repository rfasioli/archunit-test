package br.com.rfasioli.archunittest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.Test;

import br.com.rfasioli.archunittest.persistence.Dao;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class FooArchitectTest {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("br.com.rfasioli.fas");

    ////////////////////
    // Teste da camada persistence 

    @Test
    public void verificarDependenciasParaCamadaPersistencia() {

        ArchRule rule = classes()
        .that().resideInAPackage("..persistence..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");    

        rule.check(importedClasses);

    }

    @Test
    public void verificarDependenciasDaCamadaPersistencia() {

        ArchRule rule = noClasses()
        .that().resideInAPackage("..persistence..")
        .should().dependOnClassesThat().resideInAnyPackage("..service..");

        rule.check(importedClasses);

    }

    @Test
    public void verificarNomesClassesCamadaPersistencia() {

        ArchRule rule = classes()
        .that().haveSimpleNameEndingWith("Dao")
        .should().resideInAPackage("..persistence..");

        rule.check(importedClasses);

    }


    @Test
    public void verificarImplementacaoInterfaceDao() {

        ArchRule rule = classes()
        .that().implement(Dao.class)
        .should().haveSimpleNameEndingWith("Dao");

        rule.check(importedClasses);

    }

    ////////////////////
    // Teste da camada service 

    @Test
    public void verificarDependenciasParaCamadaService() {

        ArchRule rule = classes()
        .that().resideInAPackage("..service..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..service..", "..business..");    

        rule.check(importedClasses);

    }

    @Test
    public void verificarDependenciasDaCamadaService() {

        ArchRule rule = noClasses()
        .that().resideInAPackage("..service..")
        .should().dependOnClassesThat().resideInAnyPackage("..business..");

        rule.check(importedClasses);

    }

    @Test
    public void verificarNomesClassesCamadaService() {

        ArchRule rule = classes()
        .that().haveSimpleNameEndingWith("Service")
        .should().resideInAPackage("..service..");

        rule.check(importedClasses);

    }

    ////////////////////
    // Teste da camada business

    @Test
    public void verificarDependenciasParaCamadaBusiness() {

        ArchRule rule = classes()
        .that().resideInAPackage("..business..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..business..");    

        rule.check(importedClasses);

    }

    @Test
    public void verificarDependenciasDaCamadaBusiness() {

        ArchRule rule = noClasses()
        .that().resideInAPackage("..business..")
        .should().dependOnClassesThat().resideInAnyPackage("..business..");

        rule.check(importedClasses);

    }

    /////////////////////////
    //// Verificações gerais

    @Test
    public void verificarDependenciasCiclicas() {

        ArchRule rule = slices()
        .matching("br.com.rfasioli.fas.(*)..").should().beFreeOfCycles();

        rule.check(importedClasses);

    }

    @Test
    public void verificarViolacaoCamadas() {

        ArchRule rule = layeredArchitecture()
        .layer("Service").definedBy("..service..")
        .layer("Persistence").definedBy("..persistence..")
        .layer("Business").definedBy("..business..")
    
        .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")

        .whereLayer("Service").mayOnlyBeAccessedByLayers("Business");

        rule.check(importedClasses);

    }

    @Test
    public void teste() {
        classes().that().resideInAPackage("..persistence..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");
    }
    
}
