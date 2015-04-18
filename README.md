# security[![Build Status](https://drone.io/github.com/clairton/security/status.png)](https://drone.io/github.com/clairton/security/latest)
Segurança baseado em token com uso de CDI interceptors.

Adicionar no beans.xml:
```xml
<interceptors>
	<class>br.eti.clairton.security.LockInterceptor</class>
	<class>br.eti.clairton.security.GateInterceptor</class>
</interceptors>
```
Cria produces com qualifiers @App, @User e @Token:
```java
@Produces
@User
public String getUser() {
	return "admin";
}

@Produces
@App
public String getApp() {
	return "Pass";
}

@Produces
@Token
public String getToken() {
	return "123";
}
```

É necessário fornecer uma implementação de Lock e outro de Locksmith, o Lock é responsavel por identificar o usuário, enquanto o Locksmith controla os tokens de acesso.

O metodos que só podem ser acessados com identificação de usuário devem ser anotados com @Protected:
```java
@Protected
public void test() {

}
```
O métodos que só podem ser acessados mediante autorização devem ser anotados com @Authorized:
```java
@Authorized
public void test() {

}
```
Para especificar o nome do recurso pode anotadar a classe:
```java
@Resource("aplicacao")
class AplicacaoController {
...
...
}
```
Ou um metodo retornando String:
```java
class AplicacaoController {
	...
	...
	@Resource
	public String getResource() {
		return "aplicacao";
	}
}
```

Para usar será necessário adicionar os repositórios maven:

```xml
<repository>
	<id>mvn-repo-releases</id>
	<url>https://raw.github.com/clairton/mvn-repo/releases</url>
</repository>
<repository>
	<id>mvn-repo-snapshot</id>
	<url>https://raw.github.com/clairton/mvn-repo/snapshots</url>
</repository>
```
 Também adicionar as depêndencias:
```xml
<dependency>
    <groupId>br.eti.clairton</groupId>
    <artifactId>security</artifactId>
    <version>0.1.0</version>
</dependency>
```
