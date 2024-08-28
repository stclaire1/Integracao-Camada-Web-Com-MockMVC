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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Carolina Maria de Jesus', '10419244771', 7500.0, TIMESTAMP WITH TIME ZONE '1996-12-23T07:00:00Z', 0);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Chimamanda Adichie', '10114274861', 1500.0, TIMESTAMP WITH TIME ZONE '1956-09-23T07:00:00Z', 0);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Clarice Lispector', '10919444522', 3800.0, TIMESTAMP WITH TIME ZONE '1960-04-13T07:50:00Z', 2);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Conceição Evaristo', '10619244881', 1500.0, TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:00Z', 2);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Djamila Ribeiro', '10619244884', 4500.0, TIMESTAMP WITH TIME ZONE '1975-11-10T07:00:00Z', 1);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Gilberto Gil', '10419344882', 2500.0, TIMESTAMP WITH TIME ZONE '1949-05-05T07:00:00Z', 4);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jorge Amado', '10204374161', 2500.0, TIMESTAMP WITH TIME ZONE '1918-09-23T07:00:00Z', 0);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Jose Saramago', '10239254871', 5000.0, TIMESTAMP WITH TIME ZONE '1996-12-23T07:00:00Z', 0);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Lázaro Ramos', '10619244881', 2500.0, TIMESTAMP WITH TIME ZONE '1996-12-23T07:00:00Z', 2);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Silvio Almeida', '10164334861', 4500.0, TIMESTAMP WITH TIME ZONE '1970-09-23T07:00:00Z', 2);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Toni Morrison', '10219344681', 10000.0, TIMESTAMP WITH TIME ZONE '1940-02-23T07:00:00Z', 0);
        INSERT INTO tb_client (name, cpf, income, birth_date, children) VALUES('Yuval Noah Harari', '10619244881', 1500.0, TIMESTAMP WITH TIME ZONE '1956-09-23T07:00:00Z', 0);
         * 
         * 
         *
         */

        // Ana
        @Test
        @Order(1)
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
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 1L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 2L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 3L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 4L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 5L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 6L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 8L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 9L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 10L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 11L).exists())
                                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 12L).exists())
                                .andExpect(jsonPath("$.content[*].name",
                                        containsInAnyOrder("Conceição Evaristo", "Lázaro Ramos", "Clarice Lispector", "Carolina Maria de Jesus", "Gilberto Gil", "Djamila Ribeiro", "Jose Saramago", "Toni Morrison", "Yuval Noah Harari", "Chimamanda Adichie", "Silvio Almeida", "Jorge Amado")))
                                .andExpect(jsonPath("$.content[?(@.cpf == '10419244771')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10114274861')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10919444522')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10619244881')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10619244884')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10419344882')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10204374161')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10239254871')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10619244881')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10164334861')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10219344681')]").exists())
                                .andExpect(jsonPath("$.content[?(@.cpf == '10619244881')]").exists())
                                .andExpect(jsonPath("$.content[0].income").value(7500.0))
                                .andExpect(jsonPath("$.content[1].income").value(1500.0))
                                .andExpect(jsonPath("$.content[2].income").value(3800.0))
                                .andExpect(jsonPath("$.content[3].income").value(1500.0))
                                .andExpect(jsonPath("$.content[4].income").value(4500.0))
                                .andExpect(jsonPath("$.content[5].income").value(2500.0))
                                .andExpect(jsonPath("$.content[6].income").value(2500.0))
                                .andExpect(jsonPath("$.content[7].income").value(5000.0))
                                .andExpect(jsonPath("$.content[8].income").value(2500.0))
                                .andExpect(jsonPath("$.content[9].income").value(4500.0))
                                .andExpect(jsonPath("$.content[10].income").value(10000.0))
                                .andExpect(jsonPath("$.content[11].income").value(1500.0))
                                .andExpect(jsonPath("$.content[0].children").value(0))
                                .andExpect(jsonPath("$.content[1].children").value(0))
                                .andExpect(jsonPath("$.content[2].children").value(2))
                                .andExpect(jsonPath("$.content[3].children").value(2))
                                .andExpect(jsonPath("$.content[4].children").value(1))
                                .andExpect(jsonPath("$.content[5].children").value(4))
                                .andExpect(jsonPath("$.content[6].children").value(0))
                                .andExpect(jsonPath("$.content[7].children").value(0))
                                .andExpect(jsonPath("$.content[8].children").value(2))
                                .andExpect(jsonPath("$.content[9].children").value(2))
                                .andExpect(jsonPath("$.content[10].children").value(0))
                                .andExpect(jsonPath("$.content[11].children").value(0))
                                .andExpect(jsonPath("$.totalElements").exists())
                                .andExpect(jsonPath("$.totalElements").value(quantidadeClientes))
                                .andExpect(jsonPath("$.numberOfElements").exists())
                                .andExpect(jsonPath("$.numberOfElements").value(quantidadeLinhasPagina))
                                .andExpect(jsonPath("$.content[*].id",
                                                containsInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
        }

        // Isabela
        @Test
        @Order(2)
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

        // Isabela
        @Test
        @Order(3)
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
        @Order(4)
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

        //Fernanda
        @Test
        @Order(5)
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
        @Order(6)
        @DisplayName("Verificar se o endpoint get/clients/id/{id} retorna o cliente correto")
        public void testarEndPointBuscarCliente() throws Exception {
                // Arrange

                Long existingId = 8L;
                Client client = new Client(existingId, "Jose Saramago", "10239254871", 5000.0,
                                Instant.parse("1996-12-23T07:00:00Z"), 0);

                // Act
                ResultActions resultado = mockMVC
                                .perform(get("/clients/id/{id}", existingId).accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(existingId))
                                .andExpect(jsonPath("$.name").value("Jose Saramago"))
                                .andExpect(jsonPath("$.cpf").value("10239254871"))
                                .andExpect(jsonPath("$.income").value(5000.0))
                                .andExpect(jsonPath("$.birthDate").value("1996-12-23T07:00:00Z"))
                                .andExpect(jsonPath("$.children").value(0));
        }

        //Fernanda
        @Test
        @Order(7)
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

        // Ana
        @Test
        @Order(8)
        @DisplayName("Verificar se o endpoint get/clients/cpf?cpf= retorna os clientes cujo cpf contém o valor especificado")
        public void testarEndPointBuscarClientePorCPF() throws Exception {
                // Arrange
                String cpfExistente = "10619244881";
                int quantidadeEsperada = 3;

                // Act
                ResultActions resultado = mockMVC.perform(get("/clients/cpf/")
                                .param("cpf", String.valueOf(cpfExistente))
                                .accept(MediaType.APPLICATION_JSON));

                // Assert
                resultado
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").exists())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content", hasSize(quantidadeEsperada)))
                                .andExpect(jsonPath("$.content[*].cpf")
                                                .value(everyItem(equalTo(cpfExistente))));
        }

        // Ana
        @Test
        @Order(9)
        @DisplayName("Verificar se o endpoint delete/clients/{id} realmente deleta o registro cujo id foi especificado")
        public void testarEndPointDeletarClientePorId() throws Exception {
        // Arrange
        Long clienteId = 1L;

        // Act
        ResultActions resultado = mockMVC.perform(delete("/clients/{id}", clienteId)
                .accept(MediaType.APPLICATION_JSON));

        // Assign
        resultado
                .andExpect(status().isNoContent());
        }
}