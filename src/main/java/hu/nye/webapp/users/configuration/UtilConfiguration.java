package hu.nye.webapp.users.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Itt definiáltuk, hogy a modelMapper Bean hogy jöjjön létre; és itt modelMapper() kézzel lepéldányosítottuk
 *
 * külső ModelMapper osztályt csak configuration osztályban alakíthatunk Bean-é
 *
 */

@Configuration                                      // 2. itt definiálom, hogy ez egy configuration osztály azaz Bean-t lehet belőle készíteni; azaz utasítom ezzel a Springet, hogy nézd át, mikor indul az alkalmazás
public class UtilConfiguration {
    
    @Bean                                       // ez jelzi, hogy amit ez a metódus visszaad, abbol csináljon a Spring egy Bean-t
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
