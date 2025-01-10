![whispers](public/whisp.png)

# Android SDK

Official Android SDK for the [whisp](https://whsprs.ai) protocol.

---

## Table of Contents
1. [About](#about)
2. [What is Android SDK?](#what-is-android-sdk)
3. [Installation](#installation)

---

### About

Whisp is a web3 orchestrating agent designed to simplify how we interact with crypto. It relies on the Whispers Protocol to select the most suitable agent for tasks like transfers, swaps, token management and more.

> *We see AI agents as the future of web3. Whispers is an open protocol that lets developers build, orchestrate,
and embed AI agents making blockchain accessible for everyone.*

---

### What is Android SDK?

The **Whisp Android SDK** provides developers with the tools necessary to integrate Whisp's functionality into their Android applications. It offers a modular architecture to address different use cases:

- **sdk-ui**: Pre-built UI components to quickly integrate features like chat and agent interactions into existing apps.
- **sdk-be**: Backend-focused module for direct interaction with agents, signing transactions, and sending messages.
- **sdk-all**: A comprehensive package that includes both UI and backend functionality, enabling full integration of the Whispers Protocol.

> **Note**: The SDK is currently under active development. Public APIs are subject to change until the beta release.

---

### Installation

#### Create sdk instance

```kotlin
val sdk = Whispers
  .Builder(context)
  .setApiKey(apiKey)
  .setPublicKey(publicKey)
  .setEnvironment(environment)
  .setLogLevel(LogLevel.ERROR)
  .build()
```

#### Send a message

```kotlin
whispers.client.sendMessage(
    WhispSendTextMessage(data = WhispSendTextMessage.Data(text))
)
```