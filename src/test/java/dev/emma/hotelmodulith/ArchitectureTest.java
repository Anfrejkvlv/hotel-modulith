package dev.emma.hotelmodulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ArchitectureTest {

    ApplicationModules modules=ApplicationModules.of(HotelModulithApplication.class);

    @Test
    void verifyModularArchitecture(){
        assertThatNoException().isThrownBy(()->modules.verify());
    }

    @Test
    void writeDocumentationSnippets(){
        new Documenter(modules)
                .writeAggregatingDocument()
                .writeModuleCanvases()
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}
