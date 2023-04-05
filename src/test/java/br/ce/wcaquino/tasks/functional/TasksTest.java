package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\vinicius.jacques\\jenkinstest\\seleniumDrivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		//DesiredCapabilities cap = DesiredCapabilities.chrome();
		//WebDriver driver = new RemoteWebDriver(new URL("http://192.168.90.13:4444/wd/hub"), cap);
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		
		WebDriver driver = acessarAplicacao();
		try {
			//clicar em add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium 2");
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("28/03/2030");
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			//validar mensagem de sucesso
			String mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", mensagem);
		} finally {
			//fechar o browser
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarDataInvalida() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		//clicar em add todo
		driver.findElement(By.id("addTodo")).click();
		//escrever descricao
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium com erro na data");
		//escrever a data
		driver.findElement(By.id("dueDate")).sendKeys("28/03/2010");
		//clicar em salvar
		driver.findElement(By.id("saveButton")).click();
		//validar mensagem de sucesso
		String mensagem = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Due date must not be in past", mensagem);
		//fechar o browser
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		//clicar em add todo
		driver.findElement(By.id("addTodo")).click();
		//escrever descricao
		driver.findElement(By.id("task")).sendKeys("");
		//escrever a data
		driver.findElement(By.id("dueDate")).sendKeys("28/03/2040");
		//clicar em salvar
		driver.findElement(By.id("saveButton")).click();
		//validar mensagem de sucesso
		String mensagem = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the task description", mensagem);
		//fechar o browser
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		//clicar em add todo
		driver.findElement(By.id("addTodo")).click();
		//escrever descricao
		driver.findElement(By.id("task")).sendKeys("Teste teste");
		//escrever a data
		driver.findElement(By.id("dueDate")).sendKeys("");
		//clicar em salvar
		driver.findElement(By.id("saveButton")).click();
		//validar mensagem de sucesso
		String mensagem = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the due date", mensagem);
		//fechar o browser
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		//inserir tarefa
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste teste");
		driver.findElement(By.id("dueDate")).sendKeys("02/10/2050");
		driver.findElement(By.id("saveButton")).click();
		String mensagem = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Success!", mensagem);
		
		//remover a tarefa
		driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
		Assert.assertEquals("Success!", mensagem);
		
		//fechar o browser
		} finally {
		driver.quit();
		}
	}
}
