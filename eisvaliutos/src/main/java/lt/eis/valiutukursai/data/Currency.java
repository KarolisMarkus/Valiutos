/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.eis.valiutukursai.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author Karolis
 */
public class Currency {

    private String pavadinimas;
    private String valiutosKodas;
    private BigDecimal santykis1;
    private BigDecimal santykis2;
    private BigDecimal bendrasSantykis;
    private LocalDate data;
    private LocalDate data2;
    

    public Currency() {
    }

//    public Currency(String pavadinimas, String valiutosKodas, Double santykis1, LocalDate data) {
//        this.pavadinimas = pavadinimas;
//        this.valiutosKodas = valiutosKodas;
//        this.santykis1 = santykis1;
//        this.data = data;
//    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getValiutosKodas() {
        return valiutosKodas;
    }

    public void setValiutosKodas(String valiutosKodas) {
        this.valiutosKodas = valiutosKodas;
    }

    public BigDecimal getSantykis1() {
        return santykis1;
    }

    public void setSantykis1(BigDecimal santykis1) {
        this.santykis1 = santykis1;
    }

    public BigDecimal getSantykis2() {
        return santykis2;
    }

    public void setSantykis2(BigDecimal santykis2) {
        this.santykis2 = santykis2;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData2() {
        return data2;
    }

    public void setData2(LocalDate data2) {
        this.data2 = data2;
    }

    public BigDecimal getBendrasSantykis() {
        return santykis1.subtract(santykis2);
    }

    public void setBendrasSantykis(BigDecimal bendrasSantykis) {
        this.bendrasSantykis = bendrasSantykis;
    }
    
}
