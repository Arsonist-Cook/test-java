# PDF Report

### Dependência Maven
``` XML
<dependency>
	<groupId>org.arsonistcook</groupId>
	<artifactId>pdf-report</artifactId>
	<version>0.0.5-SNAPSHOT</version>
</dependency>
```

### Repositório
```
<repositories>
	<repository>
		<id>java-test-github-repo</id>
		<url>https://raw.githubusercontent.com/Arsonist-Cook/test-java/repository/</url>
		<releases>
			<enabled>true</enabled>
			<updatePolicy>daily</updatePolicy>
		</releases>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>

```


## Exemplo de uso

### Configurando o ReportPDF

Usando as configurações padrão:

```
ReportPDF report = new ReportPDF(new FileOutputStream("report.pdf"));
```

Ajustando as configuações necessárias:

```
ReportPDF report = new ReportPDF(new FileOutputStream("report.pdf"), ConfigFactory.load("myConfig"));
```

### Adicionando Dados ao Report

#### Tradicionalmente

```
report.gerarReportPDF(ReportBase.prints, ReportBase.texts, extensionContext.getDisplayName(), !scenario.isFailed());
```

#### Sugestão Formato Novo
```
Map<String, String> resultados = new LinkedHashMap<>(); //<LABEL, ENDEREÇO_PRINT>
report.gerarReportPDF(resultados, "Nome do Caso", !scenario.isFailed());
```

