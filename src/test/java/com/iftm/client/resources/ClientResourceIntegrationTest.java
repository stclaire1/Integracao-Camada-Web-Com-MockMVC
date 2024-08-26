package com.iftm.client.resources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.services.ClientService;

//necessário para utilizar o MockMVC
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourceIntegrationTest {
        @Autowired
        private MockMvc mockMVC;

        @Autowired
        private ClientService service;

        /**
         * Caso de testes : Verificar se o endpoint get/clients/ retorna todos os
         * clientes existentes
         * Arrange:
         * - base de dado : 12 clientes
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Conceição Evaristo', '10619244881', 1500.0, TIMESTAMP WITH TIME ZONE
         * '2020-07-13T20:50:00Z', 2);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Lázaro Ramos', '10619244881', 2500.0, TIMESTAMP WITH TIME ZONE
         * '1996-12-23T07:00:00Z', 2);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Clarice Lispector', '10919444522', 3800.0, TIMESTAMP WITH TIME ZONE
         * '1960-04-13T07:50:00Z', 2);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Carolina Maria de Jesus', '10419244771', 7500.0, TIMESTAMP WITH TIME
         * ZONE '1996-12-23T07:00:00Z', 0);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Gilberto Gil', '10419344882', 2500.0, TIMESTAMP WITH TIME ZONE
         * '1949-05-05T07:00:00Z', 4);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Djamila Ribeiro', '10619244884', 4500.0, TIMESTAMP WITH TIME ZONE
         * '1975-11-10T07:00:00Z', 1);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jose
         * Saramago', '10239254871', 5000.0, TIMESTAMP WITH TIME ZONE
         * '1996-12-23T07:00:00Z', 0);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Toni
         * Morrison', '10219344681', 10000.0, TIMESTAMP WITH TIME ZONE
         * '1940-02-23T07:00:00Z', 0);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Yuval
         * Noah Harari', '10619244881', 1500.0, TIMESTAMP WITH TIME ZONE
         * '1956-09-23T07:00:00Z', 0);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Chimamanda Adichie', '10114274861', 1500.0, TIMESTAMP WITH TIME ZONE
         * '1956-09-23T07:00:00Z', 0);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children)
         * VALUES('Silvio Almeida', '10164334861', 4500.0, TIMESTAMP WITH TIME ZONE
         * '1970-09-23T07:00:00Z', 2);
         * INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jorge
         * Amado', '10204374161', 2500.0, TIMESTAMP WITH TIME ZONE
         * '1918-09-23T07:00:00Z', 0); * - Uma PageRequest default
         * 
         * 
         *
         */

        @Test
        @DisplayName("Verificar se o endpoint get/clients/ retorna todos os clientes existentes")
        public void testarEndPointListarTodosClientesRetornaCorreto() throws Exception {
                // Arrange
                int quantidadeClientes = 12;
                int quantidadeLinhasPagina = 12;

                // Act

                ResultActions resultados = mockMVC.perform(get("/clients/").accept(MediaType.APPLICATION_JSON));

                // Assign
                resultados
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").exists())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 4L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 8L).exists())
                                .andExpect(jsonPath("$.content[?(@.name == '%s')]", "Toni Morrison").exists())
                                .andExpect(jsonPath("$.totalElements").exists())
                                .andExpect(jsonPath("$.totalElements").value(quantidadeClientes))
                                .andExpect(jsonPath("$.numberOfElements").exists())
                                .andExpect(jsonPath("$.numberOfElements").value(quantidadeLinhasPagina))
                                .andExpect(jsonPath("$.content[*].id",
                                                containsInAnyOrder(4, 10, 3, 1, 6, 5, 12, 7, 2, 11, 8, 9)));
        }

        // Isabela
        @Test
        @DisplayName("Verificar se o endpoint post/clients/ insere um novo cliente")
        public void testarEndPointInserirNovoCliente() throws Exception {
                // Arrange
                /*
                 * ClientDTO novoCliente = new ClientDTO(null, "Novo Cliente", "12345678901",
                 * 3000.0,
                 * Instant.parse("1990-01-01T00:00:00Z"), 1);
                 */

                // Act
                ResultActions resultado = mockMVC.perform(post("/clients/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{ \"name\": \"Novo Cliente\", \"cpf\": \"12345678901\", \"income\": 3000.0, \"birthDate\": \"1990-01-01T00:00:00Z\", \"children\": 1 }")
                                .accept(MediaType.APPLICATION_JSON));

                // Assign
                resultado
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.name").value("Novo Cliente"));
        }

        // Isabela
        @Test
        @DisplayName("Verificar se o endpoint put/clients/{id} atualiza um cliente existente")
        public void testarEndPointAtualizarCliente() throws Exception {
                // Arrange
                Long clienteId = 1L;

                // Act
                ResultActions resultado = mockMVC.perform(put("/clients/{id}", clienteId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{ \"name\": \"Cliente Atualizado\", \"cpf\": \"12345678901\", \"income\": 3500.0, \"birthDate\": \"1990-01-01T00:00:00Z\", \"children\": 2 }")
                                .accept(MediaType.APPLICATION_JSON));

                // Assign
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(clienteId))
                                .andExpect(jsonPath("$.name").value("Cliente Atualizado"))
                                .andExpect(jsonPath("$.income").value(3500.0));
        }

        // Isabela
        @Test
        @DisplayName("Verificar se o endpoint get/clients/incomeGreaterThan?income= retorna os clientes com renda maior que o valor especificado")
        public void testarEndPointBuscarClientePorSalarioMaiorQue() throws Exception {
                // Arrange
                double salarioLimiteInferior = 2500.0;
                int quantidadeEsperada = 6; // Número esperado de clientes com renda maior que o salário informado

                // Act
                ResultActions resultado = mockMVC.perform(get("/clients/incomeGreaterThan/")
                                .param("income", String.valueOf(salarioLimiteInferior))
                                .accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").exists())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content", hasSize(quantidadeEsperada)))
                                .andExpect(jsonPath("$.content[*].income")
                                                .value(everyItem(greaterThan(salarioLimiteInferior))));
        }

        //Fernanda
        @Test
        @DisplayName("Verificar se o endpoint get/clients/income retorna os clientes com renda igual ao valor especificado")
        public void testarEndPointBuscarClientePorSalario() throws Exception {
                // Arrange
                double salario = 4500.0;
                int quantidadeEsperada = 2; // Número esperado de clientes com renda igual ao salario informado

                // Act
                ResultActions resultado = mockMVC.perform(get("/clients/income/")
                                .param("income", String.valueOf(salario))
                                .accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").exists())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content", hasSize(quantidadeEsperada)))
                                .andExpect(jsonPath("$.content[*].income")
                                                .value(everyItem(equalTo(salario))));
        }

        // Fernanda
        @Test
        @DisplayName("Verificar se o endpoint get/clients/id/{id} retorna o cliente correto")
        public void testarEndPointBuscarCliente() throws Exception {
                // Arrange

                Long existingId = 8L;
                Client client = new Client(existingId, "Toni Morrison", "10219344681", 10000.0,
                                Instant.parse("1940-02-23T07:00:00Z"), 0);

                // Act
                ResultActions resultado = mockMVC
                                .perform(get("/clients/id/{id}", existingId).accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(existingId))
                                .andExpect(jsonPath("$.name").value("Toni Morrison"))
                                .andExpect(jsonPath("$.cpf").value("10219344681"))
                                .andExpect(jsonPath("$.income").value(10000.0))
                                .andExpect(jsonPath("$.birthDate").value("1940-02-23T07:00:00Z"))
                                .andExpect(jsonPath("$.children").value(0));
        }

        //Fernanda
        @Test
        @DisplayName("Verificar se o endpoint get/clients/id/{id} retorna erro para ID inexistente")
        public void testarEndPointBuscarClienteInexistente() throws Exception {
                // Arrange
                Long nonExistingId = 33L;

                // Act
                ResultActions resultado = mockMVC
                                .perform(get("/clients/id/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.timestamp").exists())
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.error").value("Resource not found"))
                                .andExpect(jsonPath("$.message").value("Entity not found"))
                                .andExpect(jsonPath("$.path").value("/clients/id/33"));
        }

}
