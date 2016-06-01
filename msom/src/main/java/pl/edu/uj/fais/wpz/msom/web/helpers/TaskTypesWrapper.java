/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author debra
 */
public class TaskTypesWrapper {
    private List<Long> ids = new ArrayList<>();
    public TaskTypesWrapper() {}
    public TaskTypesWrapper(List<Long> ids) {this.ids = ids;}
    
    public List<Long> getIds() {
        return ids;
    }
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
