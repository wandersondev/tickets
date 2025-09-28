package br.com.wandersoft.tickets.domain.usuario;

public enum UserRole {
 ADMIN("admin"),
 AGENT("agent"),
 USER("user");

 private String role;

 UserRole(String role) {
     this.role = role;
 }

 public String getRole() {
     return role;
 }
}
