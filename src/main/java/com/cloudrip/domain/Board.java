package com.cloudrip.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="board")
@ToString(exclude="reviews")
@Transactional
public class Board {

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="board_id")
   private Long boardId;
   
   @ManyToOne
   @JoinColumn(name="categoryId")
   private Category category;
   
   @OneToMany(fetch=FetchType.LAZY, mappedBy="board")
   private List<Review> reviews = new ArrayList<Review>();
   
   @Column(nullable=true)
   private String boardTitle;
   
   @Column(nullable=false)
   private String boardSubtitle1;
   
   @Column(nullable=false)
   private String boardSubtitle2;
   
   private String boardContent;
   
   private Long boardView;
   
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss", timezone = "Asia/Seoul")
   @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
   private LocalDateTime boardRegdate;
   
   
}