package com.projet.service;

import com.projet.entity.JourFerie;
import com.projet.repository.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JourFerieService {

    @Autowired
    private JourFerieRepository jourFerieRepository;

    public boolean isJourFerie(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        resetTime(cal);
        Date normalizedDate = cal.getTime();
        return jourFerieRepository.findByDate(normalizedDate).isPresent();
    }

    private void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}