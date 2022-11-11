package br.com.shinigami.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class GeradorDeSenhaArgon {
    public static void main(String[] args) {
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
//        String senha = argon2PasswordEncoder.encode("123");
//        System.out.println(senha);
        //$argon2id$v=19$m=4096,t=3,p=1$VejuwS+y79tYqdRgHj0tKA$tH+3dig+HEh8dp6e2noWUFR7iIJL1CJWAo/4i16eB2c

        String minhaSenha = "$argon2id$v=19$m=4096,t=3,p=1$VejuwS+y79tYqdRgHj0tKA$tH+3dig+HEh8dp6e2noWUFR7iIJL1CJWAo/4i16eB2c";
        boolean match = argon2PasswordEncoder.matches("123", minhaSenha);
        System.out.println(match);
    }
}
