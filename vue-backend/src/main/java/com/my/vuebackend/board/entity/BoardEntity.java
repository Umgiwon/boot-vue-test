package com.my.vuebackend.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="TB_BOARD")
@Entity
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brdNo;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="contents", nullable = false)
    private String contents;

    @Column(name="reg_nm", nullable = false)
    private String regNm;
    
    @Column(name="reg_dt", nullable = false)
    private LocalDateTime regDt;

}