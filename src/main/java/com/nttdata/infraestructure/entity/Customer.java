package com.nttdata.infraestructure.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer extends PanacheEntity {
  private String name;
  private String lastName;
  private Long nroDocument;
  private int typeCustomer;
  private int typeDocument;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
