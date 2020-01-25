package pl.bpiatek.tictactoe;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(TictactoeApplication.class);
		springApplicationBuilder.headless(false);
		springApplicationBuilder.run(args);
	}

}
