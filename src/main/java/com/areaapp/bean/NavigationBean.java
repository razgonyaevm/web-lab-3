package com.areaapp.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

// @FacesConfig(version = FacesConfig.Version.JSF_4_0)
@Named
@SessionScoped
public class NavigationBean implements Serializable {
  private static final long serialVersionUID = 1L;

  public String goToMain() {
    return "main";
  }

  public String goToIndex() {
    return "index";
  }
}
