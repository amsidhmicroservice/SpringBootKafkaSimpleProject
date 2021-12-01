package com.amsidh.mvc.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PersonRequest {
  private Integer personId;
  private String personName;
  private String address;
}
