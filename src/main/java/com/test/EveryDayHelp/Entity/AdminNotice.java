package com.test.EveryDayHelp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data // it can make getter and setter
@NoArgsConstructor

public class AdminNotice {

                                    @Id
                                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                                        private  Integer notice_id;
                                            @Column
        private  String title;
                                               @Column
                private String Description;


}
