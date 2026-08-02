package dev.emma.hotelmodulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.core.DependencyDepth;
import org.springframework.modulith.core.DependencyType;
import org.springframework.modulith.docs.Documenter;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ArchitectureTest {

    ApplicationModules modules=ApplicationModules.of(HotelModulithApplication.class);

    @Test
    void verifyModularArchitecture(){
        assertThatNoException().isThrownBy(()->modules.verify());
    }

    @Test
    void writeDocumentationSnippets(){
        Documenter.DiagramOptions options= Documenter.DiagramOptions.defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML)
                .withDependencyDepth(DependencyDepth.IMMEDIATE)
                .withDependencyTypes(DependencyType.USES_COMPONENT,DependencyType.EVENT_LISTENER)
                .withExclusions(module->module.getDisplayName().equals("reservations"))
                .withColorSelector(module->
                        switch (module.getDisplayName()) {
                            case "guests"     -> Optional.of("#2196F3");
                            case "rooms" -> Optional.of("#4CAF50");
                            case "reservations"   -> Optional.of("#FF9800");
                            case "roomreservations"   -> Optional.of("#FF0000");
                            default          -> Optional.empty();
                        })
                .withSkinParam("backgroundColor","white");

        new Documenter(modules)
                .writeModulesAsPlantUml(options)
                .writeIndividualModulesAsPlantUml(options);
    }
}
