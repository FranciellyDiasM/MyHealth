# MyHealth - Guia de Execução

Este documento ensina como configurar e rodar o projeto **MyHealth** em um emulador Android ou dispositivo físico em uma máquina Linux, além de como instalar o APK disponível na pasta de artefatos diretamente no dispositivo Android via `adb` ou download.

## Pré-requisitos

- Sistema operacional: Linux
- Java Development Kit (JDK) 11+
- Android Studio instalado (incluindo Android SDK)
- Dispositivo Android ou Emulador configurado
- `adb` (Android Debug Bridge) instalado

### Instalação do Android Studio

Caso você não tenha o Android Studio instalado, siga as instruções abaixo:

1. Baixe o Android Studio diretamente do site oficial: [Android Studio](https://developer.android.com/studio).
2. Siga as instruções para instalação na sua distribuição Linux.
3. Instale o Android SDK e configure a variável de ambiente `ANDROID_HOME` conforme as instruções fornecidas pelo Android Studio.

## Rodando o Projeto no Emulador

1. **Clone o repositório do projeto:**

   Abra o terminal e execute o comando:

   ```bash
   git clone https://github.com/FranciellyDiasM/MyHealth.git
   cd MyHealth
   ```

2. **Abra o projeto no Android Studio:**
- Abra o Android Studio e clique em Open an existing project.
- Selecione a pasta onde você clonou o projeto.
- Aguarde até que o Gradle sincronize o projeto.

3. **Configurar e executar o emulador Android:**
- No Android Studio, vá até Tools > AVD Manager.
- Crie um novo emulador ou selecione um já existente.
- Clique em Run para iniciar o emulador.

4. **Rodar o projeto no emulador:**
- Após iniciar o emulador, clique no botão de Run (ou pressione Shift + F10) no Android Studio para compilar e rodar o projeto no emulador.

### Rodando o Projeto no Dispositivo Físico
1. **Ativar modo de desenvolvedor no dispositivo:**

- No dispositivo Android, vá até Configurações > Sobre o telefone.
- Toque 7 vezes na opção Número da versão para habilitar o modo desenvolvedor.
- Volte para Configurações > Sistema > Opções do desenvolvedor.
- Habilite Depuração USB.

2. **Conectar o dispositivo via USB:**

- Conecte seu dispositivo Android à máquina Linux usando um cabo USB.
- Aceite a solicitação de depuração USB no dispositivo, se aparecer.

3. **Rodar o projeto no dispositivo:**

- No Android Studio, clique no botão de Run
- Selecione o dispositivo conectado na lista de dispositivos e clique em OK para compilar e rodar o projeto no dispositivo físico.

### Instalando o APK via Download

Se você preferir instalar o APK baixando diretamente no dispositivo, siga os passos abaixo:

1. No seu dispositivo Android, acesse o link do repositório no navegador e baixe o arquivo APK localizado na pasta [artefato](https://github.com/FranciellyDiasM/MyHealth/tree/main/artefato).
2. Após o download, vá até a pasta Downloads do dispositivo e toque no arquivo myhealth.apk.
3. Se for a primeira vez instalando aplicativos de fora da Play Store, será necessário autorizar a instalação de fontes desconhecidas:Vá até Configurações > Segurança e habilite Instalar de fontes desconhecidas.
4. Confirme a instalação do APK e, após o processo, o aplicativo estará disponível no dispositivo.

### Instalando o APK via ADB
Se você preferir instalar o APK diretamente, siga os passos abaixo:

1. **Download do APK:**
- Baixe o arquivo APK localizado na pasta [artefato](https://github.com/FranciellyDiasM/MyHealth/tree/main/artefato)
- Acesse o diretório de artefatos.
- Baixe o arquivo para sua máquina.

2. **Instalando o APK via ADB**
- Conectar o dispositivo via USB: Certifique-se de que o dispositivo Android está com a Depuração USB habilitada e conectado à máquina Linux.
- Instalar o APK via ADB: No terminal, navegue até o diretório onde o APK foi baixado e execute o seguinte comando:
```bash
Copiar código
adb install myhealth.apk
Verificar a instalação:
```
- Após a instalação, o aplicativo deve aparecer no menu de apps do dispositivo.


