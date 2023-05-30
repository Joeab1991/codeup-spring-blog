package com.codeup.codeupspringblog.repository;

import com.codeup.codeupspringblog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {

}
