package com.vlad.store.blobTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class BlobTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filetype")
    private String filetype;

    @Lob
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;


}
