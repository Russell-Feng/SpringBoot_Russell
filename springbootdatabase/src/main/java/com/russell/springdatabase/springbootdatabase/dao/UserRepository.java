package com.russell.springdatabase.springbootdatabase.dao;

import com.russell.springdatabase.springbootdatabase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
    
    
    @Query(value = "SELECT id FROM user WHERE name=?", nativeQuery = true)
    String getIdByName(String name);

//    @Select("select u.id from user u where u.username = #{username} and password = #{password}")
    @Query(value = "SELECT id FROM user WHERE name=? and password =?", nativeQuery = true)
    String getIdByNameAndPsw(String name, String password);

    @Modifying @Transactional
    @Query(value = "INSERT INTO user (name,password,age,sex,telephone) values (?,?,?,?,?)", nativeQuery = true)
    Integer register(String name, String password, Integer age, String sex, String telephone);

    @Query(value = "SELECT age FROM user WHERE name=? ", nativeQuery = true)
    String getAgeByName (String name);

    @Query(value = "SELECT telephone FROM user WHERE name=? ", nativeQuery = true)
    String getNumberByName (String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET age =? where name=?", nativeQuery = true)
    Integer modifyAgeByName(int age, String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET telephone =? where name=?", nativeQuery = true)
    Integer modifyTeleByName(String telephone, String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER SET password =? where name=?", nativeQuery = true)
    Integer modifyPswByName(String password, String name);
}
