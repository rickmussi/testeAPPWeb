/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcd.testeappweb;

import com.wcd.util.Util;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Mussi
 */
public class Integra {

    WebDriver driver;
    String componente = "/html/body/form/div/div[4]/div[1]/input";
    Util util = new Util();
    JTextArea dLog;
    DateFormat dateFormatDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    int count = 0;

    public void teste(String pUsuario, String pSenha) {
        System.setProperty("webdriver.gecko.driver", "C:\\wcd\\TestadorAPP\\geckodriver.exe");
        /*FirefoxProfile profile = new FirefoxProfile(new File("C:\\Users\\Mussi\\appdata\\Roaming\\Mozilla\\Firefox\\Profiles\\3f393amn.default-1528420934915"));*/
        FirefoxOptions fireOptions = new FirefoxOptions();
        //fireOptions.addArguments("-headless");
        //fireOptions.addArguments("-window-size=1200x600");
        fireOptions.addArguments("-start-maximized");
        //fireOptions.setProfile(profile);
        driver = new FirefoxDriver(fireOptions);
        //driver = new FirefoxDriver();
        //driver.get("http://177.23.63.131:8039/webrun/open.do?action=open&sys=S05");
        driver.get("http://servidorapp:8049/webrun/open.do?action=open&sys=S05");
        util.tempo(1000);
        logar(pUsuario, pSenha);
        util.tempo(5000);
        selecionarUnidade();
        menuEntrega();
        entregarCalendario();
        entregarCabecalho();
        entregarMedicamento();
    }

    public void navegar() {
        driver.get("http://177.23.63.131:8039/webrun/open.do?action=open&sys=S05");
    }

    public WebElement obterElementoXPath(String pXPath) {
        WebElement elemento = driver.findElement(By.xpath(pXPath));
        return elemento;
    }

    public WebElement elementoAlterarValor(String pXPath, String pValor) {
        WebElement elemento = obterElementoXPath(pXPath);
        elemento.sendKeys(pValor);
        return elemento;
    }

    public WebElement elementoClicar(String pXPath) {
        WebElement elemento = obterElementoXPath(pXPath);
        elemento.click();
        return elemento;
    }

    public WebElement elementoSubmit(String pXPath) {
        WebElement elemento = obterElementoXPath(pXPath);
        elemento.submit();
        return elemento;
    }

    public WebElement elementoLimpar(String pXPath) {
        WebElement elemento = obterElementoXPath(pXPath);
        elemento.clear();
        return elemento;
    }

    public void listarElementosPagina(String pXPath) {
        for (WebElement elemento : driver.findElements(By.xpath(pXPath))) {
            System.out.println("Elemento: " + elemento.toString());
        }

        for (Iterator iterator = driver.getWindowHandles().iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println("Elemento: " + next.toString());
        }
    }

    public void entrar() {
        //driver.findElement(By.xpath("/html/body/form/div/div[4]/div[3]/button")).click();
        driver.get("http://www.sdisistemas.com.br/so");
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException ex) {
            Logger.getLogger(Integra.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.findElement(By.xpath("//*[@id=\"id_sc_field_login\"]")).sendKeys("ricardo");
        driver.findElement(By.xpath("//*[@id=\"id_sc_field_pswd\"]")).sendKeys("123");
        driver.findElement(By.xpath("//*[@id=\"sub_form_b\"]")).click();
    }

    public void logar(String pUsuario, String pSenha) {
        WebElement iFrame = driver.findElement(By.xpath("//iframe[@name=\"mainform\"]"));
        driver.switchTo().frame(iFrame);
        elementoAlterarValor("/html/body/form/div/div[4]/div[1]/input", pUsuario); //usuario
        elementoAlterarValor("/html/body/form/div/div[4]/div[2]/input", pSenha); //senha
        elementoClicar("/html/body/form/div/div[4]/div[3]/button"); //clicar em enviar
    }

    public void selecionarUnidade() {
        util.tempo(5000);
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(1);
        d.switchTo().frame(0);
        try {
            WebElement form = d.findElement(By.name("WFRForm"));
            WebElement cmbUnidade = form.findElement(By.xpath("//*[@id=\"WFRInput740979\"]"));
            cmbUnidade.click();
            cmbUnidade.clear();
            //util.tempo(1000);
            cmbUnidade.sendKeys("CENTRO DE SAUDE MUNICIPAL");
            //util.tempo(1000);
            cmbUnidade.sendKeys(Keys.ENTER);
            util.tempo(1000);
            cmbUnidade.sendKeys(Keys.ARROW_DOWN);
            //util.tempo(1000);
            Select selectBox = new Select(d.findElement(By.id("lookupInput")));
            selectBox.selectByIndex(1);

            WebElement option = form.findElement((By.xpath("/html/body/div[3]/select/option[2]")));
            option.sendKeys(Keys.ENTER);

            WebElement btnSelecionar = form.findElement(By.xpath("//*[@id=\"btnSelecionar\"]"));
            btnSelecionar.click();
        } catch (Exception ex) {
            System.out.println("ex: " + ex.toString());
        }
    }

    public void menuEntrega() {
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(0);

        WebElement mntAtendimento = d.findElement(By.xpath("//*[@id=\"679525\"]"));
        Actions builderMntAtendimento = new Actions(d);
        builderMntAtendimento.moveToElement(mntAtendimento).build().perform();

        mntAtendimento.click();

        WebElement mntEntregaMedicamento = d.findElement(By.xpath("/html/body/form/div/div[3]/div[2]/div[3]/div/ul/li[4]/ul/li[1]/a/span"));
        Actions builderMntEntregaMedicamento = new Actions(d);
        builderMntEntregaMedicamento.moveToElement(mntEntregaMedicamento).build().perform();
        mntEntregaMedicamento.click();
    }

    public void entregarCalendario() {
        util.tempo(3000);
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(2);
        d.switchTo().frame(0);

        WebElement form = d.findElement(By.name("WFRForm"));
        WebElement btnNovo = form.findElement(By.xpath("//*[@id=\"btnNovo\"]"));
        btnNovo.click();
    }

    public void entregarCabecalho() {
        util.tempo(3000);
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(3);
        d.switchTo().frame(0);

        WebElement form = d.findElement(By.name("WFRForm"));

        WebElement cmbCidadao = form.findElement(By.id("WFRInput825391"));
        preencherCombo(form, cmbCidadao, "RICARDO", "/html/body/div[3]/select/option[2]");

        WebElement txtEntreguePara = form.findElement(By.name("WFRInput824217"));
        txtEntreguePara.sendKeys("Ricardo teste");

        WebElement cmbMedico = form.findElement(By.id("WFRInput824215"));
        preencherCombo(form, cmbMedico, "Ademar", "/html/body/div[3]/select/option[2]");

        WebElement cmbAutorizadoPor = form.findElement(By.id("WFRInput824213"));
        preencherCombo(form, cmbAutorizadoPor, "JOAZINHO", "/html/body/div[3]/select/option[2]");

        WebElement cmbEntreguePor = form.findElement(By.id("WFRInput824214"));
        preencherCombo(form, cmbEntreguePor, "PROFISSIONAL", "/html/body/div[3]/select/option[2]");

        WebElement txtProcJudicial = form.findElement(By.name("WFRInput824218"));
        txtProcJudicial.sendKeys("");

        WebElement txtObservacao = form.findElement(By.name("WFRInput824219"));
        txtObservacao.sendKeys("Observacao teste");

        WebElement btnAdicionar = form.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div[26]/button"));
        btnAdicionar.click();
    }

    public void entregarMedicamento() {
        util.tempo(3000);
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(4);
        d.switchTo().frame(0);

        WebElement form = d.findElement(By.name("WFRForm"));

        WebElement cmbMedicamento = form.findElement(By.id("WFRInput824255"));
        preencherCombo(form, cmbMedicamento, "ABAIXADOR DE LINGUA", "/html/body/div[3]/select/option[2]");

        WebElement cmbLote = form.findElement(By.id("WFRInput824256"));
        preencherCombo(form, cmbLote, "WCD", "/html/body/div[3]/select/option[1]");

        WebElement txtDataReceita = form.findElement(By.name("WFRInput833885"));
        if (!txtDataReceita.isDisplayed()) {
            txtDataReceita.sendKeys("25062018");
        }

        WebElement txtQntEnt = form.findElement(By.name("WFRInput824259"));
        txtQntEnt.sendKeys("1");

        WebElement txtQntPresc = form.findElement(By.name("WFRInput833884"));
        txtQntPresc.sendKeys("1");

        WebElement txtNumeroReceita = form.findElement(By.name("WFRInput834002"));
        if (!txtNumeroReceita.isDisplayed()) {
            txtNumeroReceita.sendKeys("1");
        }

        WebElement txtQntDias = form.findElement(By.name("WFRInput824257"));
        txtQntDias.sendKeys("1");

        WebElement btnGravar = form.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div[22]/button"));
        btnGravar.click();

        if (validarEntregarMedicamento()) {
            WebElement btnFechar = form.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div[23]/button"));
            btnFechar.click();
        }
    }

    public boolean validarEntregarMedicamento() {
        util.tempo(3000);
        WebElement formErro = null;
        WebDriver d = driver.switchTo().defaultContent(); // you are now outside both frames
        d.switchTo().frame(0);
        d.switchTo().frame(5);
        d.switchTo().frame(0);
        WebElement form = d.findElement(By.name("WFRForm"));
        try {
            formErro = form.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div[2]/div/div/div"));
        } catch (Exception ex) {
            return true;
        }
        if (formErro != null) {
            return false;
        } else {
            return true;
        }
    }

    public void preencherCombo(WebElement form, WebElement combo, String txtPreencheCombo, String xpathOption) {
        combo.click();
        combo.clear();
        util.tempo(3000);
        combo.sendKeys(txtPreencheCombo);
        util.tempo(3000);
        combo.sendKeys(Keys.ENTER);
        util.tempo(3000);
        combo.sendKeys(Keys.ARROW_DOWN);
        util.tempo(3000);
        WebElement option = form.findElement((By.xpath(xpathOption)));
        option.sendKeys(Keys.ENTER);
        util.tempo(2000);
    }

    public void imagem() {
        File imagem = new File("C:/imagem.jpg");
        java.awt.image.BufferedImage img = null;
        try {
            img = javax.imageio.ImageIO.read(imagem);
        } catch (IOException ex) {
            Logger.getLogger(Integra.class.getName()).log(Level.SEVERE, null, ex);
        }
        int w = img.getWidth();
        int h = img.getHeight();
    }

    public void setDLog(JTextArea pLog) {
        dLog = pLog;
    }

    public JTextArea setDLog() {
        return dLog;
    }

    public void addTextoLog(String pTexto) {
        String dataHora = dateFormatDataHora.format(new java.util.Date());
        dLog.append(dataHora + " - " + pTexto);        
        dLog.append("\n");
        atualizaLog();        
    }

    public void atualizaLog() {
        dLog.requestFocus();
        dLog.setCaretPosition(dLog.getText().length());
        count += 1;
    }

}
