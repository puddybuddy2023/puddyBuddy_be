package com.example.puddyBuddy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "breed_tags")
@Getter
@Setter
public class BreedTag {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "breed_tag_id")
        private Long breedTagId;

        @Column(name = "breed_tag_name")
        private String breedTagName;

        @Column(name = "breed_tag_code")
        private String breedTagCode;

        @ManyToOne
        @JoinColumn(name = "pet_size_total_id")
        private PetsizeTotal petsizeTotal;
}
