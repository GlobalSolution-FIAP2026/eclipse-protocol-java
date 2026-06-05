# 🌿 Eclipse Protocol

> Plataforma de monitoramento inteligente para o agronegócio via sensores IoT e camada espacial de satélites

---

## 👥 Equipe

**Turma:** 2TDSPO — FIAP

| Aluno | RM |
|---|---|
| Gustavo Gomes Martins | 555999 |
| Pedro dos Anjos | 563832 |
| Matheus de Mattos Vecchi | 561716 |
| Nicholas Albuquerque Buzo | 561082 |
| Nicholas Camillo Canadas de Paula | 561262 |

---

## 📋 Sobre o Projeto

O **Eclipse Protocol** é uma plataforma de monitoramento inteligente para o agronegócio, desenvolvida para auxiliar produtores rurais no acompanhamento de plantações através de **sensores IoT**, leituras ambientais e geração automática de alertas.

A solução permite o gerenciamento de usuários, propriedades rurais, plantações, sensores, leituras ambientais e alertas, centralizando informações importantes para apoiar a **tomada de decisão no campo**.

Além da camada IoT, o projeto conta com uma **camada espacial** que gerencia satélites, imagens de satélite, lixo espacial e riscos orbitais, preparada para futuras integrações com **Copernicus, Sentinel, NDVI e Oracle Graph**.

---

## 🎯 Objetivos

- ✅ Gerenciar propriedades rurais e plantações
- ✅ Monitorar condições ambientais através de sensores IoT
- ✅ Armazenar leituras de temperatura, umidade, precipitação e NDVI
- ✅ Gerar alertas baseados em condições críticas
- ✅ Disponibilizar autenticação segura via JWT
- ✅ Autenticação OAuth2 com GitHub
- ✅ Fornecer documentação interativa através do Swagger/OpenAPI
- ✅ Tratamento global de exceções com respostas padronizadas
- ✅ Regras de negócio para integridade referencial
- ✅ Navegação HATEOAS em todos os recursos REST
- ✅ Gerenciar satélites e suas imagens capturadas
- ✅ Rastrear objetos de lixo espacial
- ✅ Analisar riscos orbitais entre satélites e lixo espacial

---

## 🛠️ Tecnologias Utilizadas

### Backend
| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Web MVC | — |
| Spring Data JPA | — |
| Spring Security | — |
| Spring Security OAuth2 Client | — |
| OAuth2 Resource Server | — |
| JWT (Nimbus JOSE) | — |
| Spring HATEOAS | — |
| Lombok | — |
| Bean Validation | — |

### Banco de Dados
| Tecnologia | Descrição |
|---|---|
| H2 Database | Banco em memória (desenvolvimento) |
| JPA / Hibernate | ORM com dialeto H2 |

### Documentação
| Tecnologia | Versão |
|---|---|
| SpringDoc OpenAPI | 3.0.2 |
| Swagger UI | — |

### Ferramentas
- Maven
- Git / GitHub
- IntelliJ IDEA
- Postman

---

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/fiap/eclipseprotocol/
├── config/
│   ├── JwtConfig.java                      # Configuração do bean JWT (chave secreta)
│   ├── OpenApiConfig.java                  # Configuração Swagger/OpenAPI com suporte JWT
│   └── SecurityConfig.java                 # Configuração Spring Security + JWT + OAuth2
├── controller/
│   ├── AuthController.java                 # Autenticação (login / geração de token)
│   ├── AlertaController.java
│   ├── LeituraController.java
│   ├── LocalizacaoController.java
│   ├── PlantacaoController.java
│   ├── PropriedadeController.java
│   ├── SensorController.java
│   ├── UsuarioController.java
│   ├── SateliteController.java             # [NOVO] Camada espacial
│   ├── ImagemSateliteController.java       # [NOVO] Camada espacial
│   ├── LixoEspacialController.java         # [NOVO] Camada espacial
│   └── RiscoOrbitalController.java         # [NOVO] Camada espacial
├── dto/
│   ├── request/                            # DTOs de entrada (Request)
│   └── response/                          # DTOs de saída (Response) — extends RepresentationModel (HATEOAS)
├── exception/
│   ├── BusinessException.java             # Exceção para regras de negócio
│   ├── ErrorResponse.java                 # Formato padronizado de erro
│   ├── GlobalExceptionHandler.java        # Handler global de exceções
│   └── ResourceNotFoundException.java    # Exceção para recurso não encontrado
├── model/
│   ├── Alerta.java
│   ├── Leitura.java
│   ├── Localizacao.java
│   ├── Plantacao.java
│   ├── Propriedade.java
│   ├── Sensor.java
│   ├── Usuario.java
│   ├── Satelite.java                      # [NOVO] Camada espacial
│   ├── ImagemSatelite.java                # [NOVO] Camada espacial
│   ├── LixoEspacial.java                  # [NOVO] Camada espacial
│   └── RiscoOrbital.java                  # [NOVO] Camada espacial
├── repository/                            # Interfaces JPA Repository
├── security/
│   └── OAuth2SuccessHandler.java          # Handler de sucesso OAuth2 → geração de JWT
├── service/
│   ├── AlertaService.java
│   ├── LeituraService.java
│   ├── LocalizacaoService.java
│   ├── PlantacaoService.java
│   ├── PropriedadeService.java
│   ├── SensorService.java
│   ├── TokenService.java                  # Geração e validação de JWT
│   ├── UsuarioService.java
│   ├── SateliteService.java               # [NOVO] Camada espacial
│   ├── ImagemSateliteService.java         # [NOVO] Camada espacial
│   ├── LixoEspacialService.java           # [NOVO] Camada espacial
│   └── RiscoOrbitalService.java           # [NOVO] Camada espacial
└── EclipseProtocolApplication.java
```

---

## 📦 Modelos de Dados

### 🧑 Usuario
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nome | String | Nome completo |
| email | String | E-mail único |
| senha | String | Senha de acesso |
| ativo | Boolean | Status do usuário |
| dataCriacao | LocalDateTime | Data de criação |

### 📍 Localizacao
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| cidade | String | Cidade |
| estado | String | Estado (UF) |
| pais | String | País |
| coordenadas | **@Embedded** `Coordenadas` | Objeto embutido com latitude e longitude |
| coordenadas.latitude | Double | Latitude geográfica |
| coordenadas.longitude | Double | Longitude geográfica |
| cep | String | CEP |

> `Coordenadas` é uma classe `@Embeddable` dentro de `Localizacao`. Os campos `latitude` e `longitude` são persistidos diretamente nas colunas `NR_LATITUDE` e `NR_LONGITUDE` via `@AttributeOverride`.

### 🏡 Propriedade
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nome | String | Nome da propriedade |
| areaTotal | Double | Área total (ha) |
| tipoSolo | String | Tipo do solo |
| localizacao | Localizacao | FK localização |
| usuario | Usuario | FK proprietário |

### 🌱 Plantacao
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nome | String | Nome da plantação |
| cultura | String | Tipo de cultura |
| areaHectares | Double | Área em hectares |
| status | String | Status atual |
| propriedade | Propriedade | FK propriedade |

### 📡 Sensor
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nome | String | Nome do sensor |
| tipo | String | Tipo do sensor |
| ativo | Boolean | Status do sensor |
| plantacao | Plantacao | FK plantação |

> `Sensor` usa `@Inheritance(SINGLE_TABLE)`. O subtipo `SensorEspecializado` estende `Sensor` adicionando o campo `unidadeMedida`. O discriminador é a coluna `DS_SUBTIPO`.

### 📊 Leitura
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| temperatura | Double | Temperatura (°C) |
| umidade | Double | Umidade (%) |
| precipitacao | Double | Precipitação (mm) |
| ndvi | Double | Índice NDVI |
| dataLeitura | LocalDateTime | Data/hora da leitura |
| sensor | Sensor | FK sensor |

### 🚨 Alerta
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| tipoAlerta | Enum | Tipo do alerta |
| severidade | Enum | Nível de severidade |
| mensagem | String | Descrição do alerta |
| status | Enum | Status do alerta |
| dataCriacao | LocalDateTime | Data/hora de criação |
| leitura | Leitura | FK leitura |
| plantacao | Plantacao | FK plantação |

#### Enums do Alerta
| Enum | Valores |
|---|---|
| TipoAlerta | `TEMP_ALTA`, `TEMP_BAIXA`, `UMID_ALTA`, `UMID_BAIXA`, `NDVI_CRITICO`, `PRECIPITACAO_EXCESSIVA` |
| Severidade | `BAIXA`, `MEDIA`, `ALTA`, `CRITICA` |
| StatusAlerta | `ABERTO`, `RECONHECIDO`, `RESOLVIDO` |

---

### 🛰️ Satelite *(Camada Espacial)*
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nome | String | Nome do satélite |
| tipo | String | Tipo do satélite |
| orbita | String | Tipo de órbita |
| altitudeKm | Double | Altitude em km |
| status | Enum | Status operacional |
| dataLancamento | LocalDate | Data de lançamento |

#### Enum StatusSatelite
| Valor | Descrição |
|---|---|
| `ATIVO` | Satélite em operação |
| `INATIVO` | Satélite fora de operação |
| `DESCOMISSIONADO` | Satélite desativado permanentemente |

---

### 🖼️ ImagemSatelite *(Camada Espacial)*
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| satelite | Satelite | FK satélite (N:1) |
| plantacao | Plantacao | FK plantação (N:1) |
| urlImagem | String | URL da imagem capturada |
| ndvi | Double | Índice NDVI da imagem |
| coberturaNuvem | Double | % de cobertura de nuvens |
| dataCaptura | LocalDateTime | Data/hora da captura |

---

### 🗑️ LixoEspacial *(Camada Espacial)*
| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador único |
| nomeObjeto | String | Nome/identificação do objeto |
| tipoObjeto | String | Tipo do objeto |
| altitudeKm | Double | Altitude em km |
| velocidadeKmh | Double | Velocidade em km/h |
| orbita | String | Órbita do objeto |
| dataIdentificacao | LocalDate | Data de identificação |

---

### ⚠️ RiscoOrbital *(Camada Espacial)*
| Campo | Tipo | Descrição |
|---|---|---|
| idSatelite + idLixoEspacial | **Chave composta** (`@EmbeddedId`) | Par único que identifica o risco |
| satelite | Satelite | FK satélite (N:1) — parte da chave |
| lixoEspacial | LixoEspacial | FK lixo espacial (N:1) — parte da chave |
| nivelRisco | Enum | Nível de risco identificado |
| descricaoRisco | String | Descrição detalhada do risco |
| dataAnalise | LocalDateTime | Data/hora da análise |

> A chave primária é composta por `(idSatelite, idLixoEspacial)`. Só pode existir **um registro de risco por par satélite/debris**.

#### Enum NivelRisco
| Valor | Descrição |
|---|---|
| `BAIXO` | Risco baixo |
| `MODERADO` | Risco moderado |
| `ALTO` | Risco alto |
| `CRITICO` | Risco crítico |

---

## 🏛️ Modelagem Avançada (JPA)

O projeto implementa os três pilares de modelagem avançada exigidos:

### 1. Herança — `Sensor` + `SensorEspecializado`
Estratégia **SINGLE_TABLE**: ambas as classes compartilham a tabela `TB_SENSOR`. A coluna `DS_SUBTIPO` distingue o tipo:
- `GENERICO` → instâncias de `Sensor`
- `ESPECIALIZADO` → instâncias de `SensorEspecializado` (adiciona o campo `unidadeMedida`)

### 2. @Embeddable / @Embedded — `Localizacao.Coordenadas`
A classe interna `Coordenadas` é anotada com `@Embeddable` e contém `latitude` e `longitude`. O campo `coordenadas` em `Localizacao` usa `@Embedded` com `@AttributeOverrides` para manter os nomes de colunas originais (`NR_LATITUDE`, `NR_LONGITUDE`).

### 3. Chave Composta — `RiscoOrbital.RiscoOrbitalId`
A classe interna `RiscoOrbitalId` é anotada com `@Embeddable` e contém `idSatelite` e `idLixoEspacial`. O campo `id` em `RiscoOrbital` usa `@EmbeddedId`, e as associações `@ManyToOne` usam `@MapsId` para sincronizar automaticamente os valores do FK com a chave composta.

---

## 📝 Exemplos de Corpo para Requisições POST

Siga esta ordem ao cadastrar pela primeira vez (as entidades têm dependências entre si).

### 1. Criar Usuário — `POST /usuarios`
*(Endpoint público — não precisa de token)*
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123"
}
```

### 2. Login — `POST /auth/login`
*(Retorna o token JWT — use-o em todas as requisições seguintes)*
```json
{
  "email": "joao@email.com",
  "senha": "senha123"
}
```
**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer"
}
```

---

### 3. Criar Localização — `POST /localizacoes`
```json
{
  "cidade": "Ribeirão Preto",
  "estado": "SP",
  "pais": "Brasil",
  "latitude": -21.1767,
  "longitude": -47.8208,
  "cep": "14010-000"
}
```

### 4. Criar Propriedade — `POST /propriedades`
*(Precisa de `idLocalizacao` e `idUsuario` já criados)*
```json
{
  "nome": "Fazenda São João",
  "proprietario": "João Silva",
  "areaTotal": 150.5,
  "tipoSolo": "Argiloso",
  "idLocalizacao": 1,
  "idUsuario": 1
}
```

### 5. Criar Plantação — `POST /plantacoes`
*(Precisa de `idPropriedade` já criado)*
```json
{
  "nome": "Plantação Norte",
  "cultura": "Soja",
  "areaHectares": 50.0,
  "status": "Ativo",
  "idPropriedade": 1
}
```

### 6. Criar Sensor — `POST /sensores`
*(Precisa de `idPlantacao` já criado)*
```json
{
  "nome": "Sensor Temperatura 01",
  "tipo": "TEMPERATURA",
  "idPlantacao": 1
}
```

### 7. Registrar Leitura — `POST /leituras`
*(Precisa de `sensor` — ID do sensor — já criado. `dataLeitura` é preenchida automaticamente pelo servidor)*
```json
{
  "sensor": 1,
  "temperatura": 28.5,
  "umidade": 65.0,
  "precipitacao": 12.3,
  "ndvi": 0.75
}
```

### 8. Criar Alerta — `POST /alertas`
*(Precisa de `idLeitura` e `idPlantacao` já criados)*

**Valores válidos para `tipoAlerta`:** `TEMP_ALTA`, `TEMP_BAIXA`, `UMID_ALTA`, `UMID_BAIXA`, `NDVI_CRITICO`, `PRECIPITACAO_EXCESSIVA`

**Valores válidos para `severidade`:** `BAIXA`, `MEDIA`, `ALTA`, `CRITICA`
```json
{
  "idLeitura": 1,
  "idPlantacao": 1,
  "tipoAlerta": "TEMP_ALTA",
  "severidade": "MEDIA",
  "mensagem": "Temperatura acima do limite recomendado para a cultura."
}
```

---

### 9. Cadastrar Satélite — `POST /satelites`

**Valores válidos para `status`:** `ATIVO`, `INATIVO`, `DESCOMISSIONADO`
```json
{
  "nome": "Sentinel-2A",
  "tipo": "Observação da Terra",
  "orbita": "LEO",
  "altitudeKm": 786.0,
  "status": "ATIVO",
  "dataLancamento": "2015-06-23"
}
```

### 10. Registrar Lixo Espacial — `POST /lixo-espacial`
```json
{
  "nomeObjeto": "Debris-2024-001",
  "tipoObjeto": "Fragmento de foguete",
  "altitudeKm": 600.0,
  "velocidadeKmh": 28000.0,
  "orbita": "LEO",
  "dataIdentificacao": "2024-01-15"
}
```

### 11. Registrar Imagem de Satélite — `POST /imagens-satelite`
*(Precisa de `idSatelite` e `idPlantacao` já criados)*
```json
{
  "idSatelite": 1,
  "idPlantacao": 1,
  "urlImagem": "https://copernicus.eu/images/2026/sentinel2_001.jpg",
  "ndvi": 0.72,
  "coberturaNuvem": 5.3,
  "dataCaptura": "2026-06-05T08:00:00"
}
```

### 12. Registrar Risco Orbital — `POST /riscos-orbitais`
*(Precisa de `idSatelite` e `idLixoEspacial` já criados. A chave primária é o par — só pode haver UM risco por combinação)*

**Valores válidos para `nivelRisco`:** `BAIXO`, `MODERADO`, `ALTO`, `CRITICO`
```json
{
  "idSatelite": 1,
  "idLixoEspacial": 1,
  "nivelRisco": "ALTO",
  "descricaoRisco": "Aproximação crítica detectada a menos de 500 metros.",
  "dataAnalise": "2026-06-05T09:00:00"
}
```

---

Todos os endpoints de recursos retornam links de navegação hypermedia no campo `_links`:

```json
{
  "id": 1,
  "nome": "Sentinel-2",
  "_links": {
    "self":    { "href": "http://localhost:8080/satelites/1" },
    "todos":   { "href": "http://localhost:8080/satelites" },
    "deletar": { "href": "http://localhost:8080/satelites/1" }
  }
}
```

Listagens retornam `CollectionModel` com `_embedded` e `_links`. O nome da chave dentro de `_embedded` é gerado automaticamente pelo Spring HATEOAS a partir do nome da classe Response:

```json
{
  "_embedded": {
    "sateliteResponseList": [],
    "imagemSateliteResponseList": [],
    "lixoEspacialResponseList": [],
    "riscoOrbitalResponseList": [],
    "alertaResponseList": []
  },
  "_links": {
    "self": { "href": "http://localhost:8080/satelites" }
  }
}
```

> 💡 O padrão do nome é: `<nomeClasseResponse>List` (ex: `SateliteResponse` → `sateliteResponseList`)

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com algoritmo **HS256** via Spring Security OAuth2 Resource Server.

> ⚠️ As senhas são armazenadas com **BCrypt**. Ao criar um usuário via `POST /usuarios`, a senha é automaticamente cifrada. No login, a comparação é feita com `passwordEncoder.matches()` — envie sempre a senha em texto puro no body.

### Rotas públicas (sem autenticação)
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/auth/login` | Gera token JWT via email/senha |
| POST | `/usuarios` | Cria novo usuário |
| GET | `/swagger-ui/**` | Documentação Swagger |
| GET | `/v3/api-docs/**` | OpenAPI Docs |
| GET | `/h2-console/**` | Console H2 |
| GET | `/oauth2/**` | Login via GitHub |

> Todas as demais rotas exigem o header `Authorization: Bearer <token>`

---

### 🔑 Autenticação via Email e Senha

Fluxo principal recomendado, especialmente para integração com o aplicativo mobile.

**1. Realizar login:**
```http
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer"
}
```

**2. Usar o token nas requisições protegidas:**
```http
GET /propriedades
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

### 🐙 Autenticação via GitHub (OAuth2)

O projeto também suporta login social com GitHub como opção complementar.

**Fluxo implementado:**
```
Usuário → GitHub OAuth2 → Spring Security → OAuth2SuccessHandler → JWT próprio da API → Acesso aos recursos
```

> ⚠️ As credenciais OAuth2 não são armazenadas no código-fonte. São lidas a partir de variáveis de ambiente:
> - `GITHUB_CLIENT_ID`
> - `GITHUB_CLIENT_SECRET`

Para configurar localmente, utilize o arquivo `application-example.properties` como referência e defina as variáveis de ambiente no seu sistema ou IDE.

---

## 🤝 Integração Mobile

O fluxo recomendado para o aplicativo mobile é:

```
1. POST /auth/login  →  Enviar email e senha
2. Receber token JWT na resposta
3. Armazenar o token localmente no dispositivo
4. Enviar o token em todas as requisições via header:
   Authorization: Bearer <TOKEN>
```

> O login via GitHub está disponível e poderá ser integrado futuramente ao aplicativo mobile como opção adicional de autenticação.

---

## 📖 Swagger — Testando Endpoints Protegidos

A documentação Swagger está configurada com suporte à autenticação JWT.

**Passos:**
1. Acesse: **http://localhost:8080/swagger-ui/index.html**
2. Execute `POST /auth/login` para obter o token
3. Clique no botão **Authorize** 🔒
4. Insira o valor: `Bearer <seu_token>`
5. Confirme e teste os endpoints protegidos normalmente

---

## ⚠️ Tratamento de Exceções

O projeto implementa tratamento global de exceções via `GlobalExceptionHandler`, retornando respostas padronizadas:

| Status | Descrição |
|---|---|
| `400 Bad Request` | Dados inválidos na requisição |
| `401 Unauthorized` | Token ausente, inválido ou expirado |
| `404 Not Found` | Recurso não encontrado (`ResourceNotFoundException`) |
| `409 Conflict` | Violação de regra de negócio (`BusinessException`) |
| `500 Internal Server Error` | Erros inesperados no servidor |

---

## 📏 Regras de Negócio

O sistema impede a exclusão de entidades que possuam relacionamentos ativos, garantindo a integridade dos dados:

| Entidade | Restrição de exclusão |
|---|---|
| Usuário | Não pode ser excluído se vinculado a uma propriedade |
| Localização | Não pode ser excluída se vinculada a uma propriedade |
| Propriedade | Não pode ser excluída se vinculada a plantações |
| Plantação | Não pode ser excluída se vinculada a sensores |
| Sensor | Não pode ser excluído se vinculado a leituras |
| Leitura | Não pode ser excluída se vinculada a alertas |
| Satélite | Não pode ser excluído se vinculado a imagens ou riscos orbitais |
| Lixo Espacial | Não pode ser excluído se vinculado a riscos orbitais |

---

## 🌐 Endpoints da API

### Autenticação
| Método | Endpoint | Descrição | Auth |
|---|---|---|--|
| POST | `/auth/login` | Realiza login e retorna token JWT | ✅ |

### Usuários
| Método | Endpoint | Descrição | Auth |
|---|---|---|--|
| GET | `/usuarios` | Lista todos os usuários | ✅ |
| GET | `/usuarios/{id}` | Busca usuário por ID | ✅ |
| POST | `/usuarios` | Cria novo usuário | ✅ |
| PUT | `/usuarios/{id}` | Atualiza usuário | ✅ |
| DELETE | `/usuarios/{id}` | Remove usuário | ✅ |

### Localizações
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/localizacoes` | Lista todas as localizações | ✅ |
| GET | `/localizacoes/{id}` | Busca localização por ID | ✅ |
| POST | `/localizacoes` | Cria nova localização | ✅ |
| PUT | `/localizacoes/{id}` | Atualiza localização | ✅ |
| DELETE | `/localizacoes/{id}` | Remove localização | ✅ |

### Propriedades
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/propriedades` | Lista todas as propriedades | ✅ |
| GET | `/propriedades/{id}` | Busca propriedade por ID | ✅ |
| POST | `/propriedades` | Cria nova propriedade | ✅ |
| PUT | `/propriedades/{id}` | Atualiza propriedade | ✅ |
| DELETE | `/propriedades/{id}` | Remove propriedade | ✅ |

### Plantações
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/plantacoes` | Lista todas as plantações | ✅ |
| GET | `/plantacoes/{id}` | Busca plantação por ID | ✅ |
| POST | `/plantacoes` | Cria nova plantação | ✅ |
| PUT | `/plantacoes/{id}` | Atualiza plantação | ✅ |
| DELETE | `/plantacoes/{id}` | Remove plantação | ✅ |

### Sensores
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/sensores` | Lista todos os sensores | ✅ |
| GET | `/sensores/{id}` | Busca sensor por ID | ✅ |
| POST | `/sensores` | Cria novo sensor | ✅ |
| PUT | `/sensores/{id}` | Atualiza sensor | ✅ |
| DELETE | `/sensores/{id}` | Remove sensor | ✅ |

### Leituras
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/leituras` | Lista todas as leituras | ✅ |
| GET | `/leituras/{id}` | Busca leitura por ID | ✅ |
| POST | `/leituras` | Registra nova leitura | ✅ |
| PUT | `/leituras/{id}` | Atualiza leitura | ✅ |
| DELETE | `/leituras/{id}` | Remove leitura | ✅ |

### Alertas
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/alertas` | Lista todos os alertas | ✅ |
| GET | `/alertas/{id}` | Busca alerta por ID | ✅ |
| POST | `/alertas` | Cria novo alerta | ✅ |
| PUT | `/alertas/{id}` | Atualiza alerta | ✅ |
| DELETE | `/alertas/{id}` | Remove alerta | ✅ |

### Satélites *(Camada Espacial)*
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/satelites` | Lista todos os satélites | ✅ |
| GET | `/satelites/{id}` | Busca satélite por ID | ✅ |
| POST | `/satelites` | Cadastra novo satélite | ✅ |
| PUT | `/satelites/{id}` | Atualiza satélite | ✅ |
| DELETE | `/satelites/{id}` | Remove satélite | ✅ |

### Imagens de Satélite *(Camada Espacial)*
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/imagens-satelite` | Lista todas as imagens | ✅ |
| GET | `/imagens-satelite/{id}` | Busca imagem por ID | ✅ |
| POST | `/imagens-satelite` | Registra nova imagem | ✅ |
| PUT | `/imagens-satelite/{id}` | Atualiza imagem | ✅ |
| DELETE | `/imagens-satelite/{id}` | Remove imagem | ✅ |

### Lixo Espacial *(Camada Espacial)*
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/lixo-espacial` | Lista todos os objetos | ✅ |
| GET | `/lixo-espacial/{id}` | Busca objeto por ID | ✅ |
| POST | `/lixo-espacial` | Registra novo objeto | ✅ |
| PUT | `/lixo-espacial/{id}` | Atualiza objeto | ✅ |
| DELETE | `/lixo-espacial/{id}` | Remove objeto | ✅ |

### Riscos Orbitais *(Camada Espacial)*
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/riscos-orbitais` | Lista todos os riscos | ✅ |
| GET | `/riscos-orbitais/{idSatelite}/{idLixoEspacial}` | Busca risco pela chave composta | ✅ |
| POST | `/riscos-orbitais` | Registra novo risco | ✅ |
| PUT | `/riscos-orbitais/{idSatelite}/{idLixoEspacial}` | Atualiza risco | ✅ |
| DELETE | `/riscos-orbitais/{idSatelite}/{idLixoEspacial}` | Remove risco | ✅ |

---

## ▶️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Variáveis de ambiente configuradas (necessário para OAuth2 com GitHub):
  - `GITHUB_CLIENT_ID`
  - `GITHUB_CLIENT_SECRET`

> Consulte o arquivo `application-example.properties` na raiz do projeto para referência de configuração.

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/eclipse-protocol.git

# Acesse a pasta do projeto
cd EclipseProtocol

# Execute com Maven
./mvnd spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 🔗 URLs Importantes

| Recurso | URL |
|---|---|
| API Base | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI Docs | http://localhost:8080/v3/api-docs |
| Login GitHub | http://localhost:8080/oauth2/authorization/github |

### Configuração Oracle
Está no application.properties

---

## 🧪 Testando com Postman

Importe o arquivo `EclipseProtocol.postman_collection.json` disponível na raiz do projeto.

### Ordem recomendada para os testes

**Camada IoT:**
```
1. POST /usuarios              → Cadastrar usuário
2. POST /auth/login            → Obter token JWT
3. POST /localizacoes          → Criar localização
4. POST /propriedades          → Criar propriedade (usa idUsuario + idLocalizacao)
5. POST /plantacoes            → Criar plantação (usa idPropriedade)
6. POST /sensores              → Criar sensor (usa idPlantacao)
7. POST /leituras              → Registrar leitura (usa idSensor)
8. POST /alertas               → Criar alerta (usa idLeitura + idPlantacao)
```

**Camada Espacial:**
```
9.  POST /satelites            → Cadastrar satélite
10. POST /lixo-espacial        → Registrar objeto de lixo espacial
11. POST /imagens-satelite     → Registrar imagem (usa idSatelite + idPlantacao)
12. POST /riscos-orbitais      → Registrar risco (usa idSatelite + idLixoEspacial)
```

> ⚠️ Após o login, adicione o token JWT no header de todas as requisições protegidas:
> `Authorization: Bearer <seu_token>`

---

## 📁 Banco de Dados — Tabelas

| Tabela | Entidade |
|---|---|
| TB_USUARIO | Usuario |
| TB_LOCALIZACAO | Localizacao |
| TB_PROPRIEDADE | Propriedade |
| TB_PLANTACAO | Plantacao |
| TB_SENSOR | Sensor |
| TB_LEITURA | Leitura |
| TB_ALERTA | Alerta |
| TB_SATELITE | Satelite |
| TB_IMAGEM_SATELITE | ImagemSatelite |
| TB_LIXO_ESPACIAL | LixoEspacial |
| TB_RISCO_ORBITAL | RiscoOrbital |

> O banco oracle.

---

## 🚀 Próximas Integrações Previstas

A camada espacial foi desenvolvida para suportar futuras integrações com:

| Integração | Descrição |
|---|---|
| **Copernicus** | Dados de observação da Terra da ESA |
| **Sentinel** | Imagens de satélite de alta resolução |
| **NDVI API** | Índices de vegetação em tempo real |
| **Oracle Graph** | Análise de relacionamentos entre objetos espaciais |

---

## 📄 Licença

Projeto acadêmico desenvolvido para a disciplina de **Análise de Desenvolvimento de Sistemas I.A** — FIAP 2026.
