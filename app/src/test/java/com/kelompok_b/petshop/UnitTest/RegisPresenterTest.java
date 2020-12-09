package com.kelompok_b.petshop.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisPresenterTest {
    @Mock
    private RegisView view;
    @Mock
    private RegisService service;
    private RegisPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new RegisPresenter(view, service);
    }

    @Test
    public void inputKosong() throws Exception {
        when(view.getName()).thenReturn("");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
        verify(view).showPasswordError("Inputan Tidak Boleh Kosong");
    }

    @Test
    public void nameKosong() throws Exception {
        when(view.getName()).thenReturn("");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
        verify(view).showPasswordError("Nama Tidak Boleh Kosong");
    }

    @Test
    public void ageKosong() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("password : " + view.getPassword());
        when(service.getValid(view, view.getName(), view.getAge(), view.getGender(), view.getEmail(),
                view.getPassword())).thenReturn(false);
        presenter.onRegisClicked();
        verify(view).showPasswordError("Umur Tidak Boleh Kosong");
    }

    @Test
    public void genderKosong() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
        verify(view).showPasswordError("Jenis Kelamin Tidak Boleh Kosong");
    }

    @Test
    public void emailKosong() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
        verify(view).showPasswordError("Email Tidak Boleh Kosong");
    }

    @Test
    public void passwordKosong() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
        verify(view).showPasswordError("Password Tidak Boleh Kosong");
    }

    @Test
    public void passwordKurang() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123");
        System.out.println("password : " + view.getPassword());
        presenter.onRegisClicked();
//        when(service.getValid(view, view.getName(), view.getAge(), view.getGender(), view.getEmail(),
//                view.getPassword())).thenReturn(true);
        verify(view).showPasswordError("Password Kurang dari 6 digit");
    }

    @Test
    public void behasilRegis() throws Exception {
        when(view.getName()).thenReturn("kaleb");
        System.out.println("nama : " + view.getName());
        when(view.getAge()).thenReturn("10");
        System.out.println("umur : " + view.getAge());
        when(view.getGender()).thenReturn("lelaki");
        System.out.println("jenis kelmain : " + view.getGender());
        when(view.getEmail()).thenReturn("kaleb@gmail.com");
        System.out.println("email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("password : " + view.getName());
        when(service.getValid(view, view.getName(), view.getAge(), view.getGender(), view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getName(), view.getEmail(), view.getAge(), view.getGender(),
                view.getPassword()));
        presenter.onRegisClicked();
    }
}