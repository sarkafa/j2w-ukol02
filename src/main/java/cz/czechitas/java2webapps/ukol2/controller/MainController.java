package cz.czechitas.java2webapps.ukol2.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            //Metoda lines() vrací stream řádků ze souboru.
            //Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/")
    public ModelAndView getQuote() throws IOException {
        ModelAndView result = new ModelAndView("index"); // bez lomitka a suffixu, templates jsou v resources
        List<String> seznamTextu = new ArrayList<>(); // Initialize the list before the try-catch block
        seznamTextu = MainController.readAllLines("citaty.txt");
        Random random = new Random();
        int randomIndex = random.nextInt(seznamTextu.size());
        String randomQuote = seznamTextu.get(randomIndex);
        result.addObject("quote", randomQuote);
        return result;
    }

}

