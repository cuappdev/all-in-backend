package com.example.allin.scraper;
import com.example.allin.playerData.PlayerData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

@Service
public class ScraperService {
  List<String> urls;

  public Set<PlayerData> getStatistics() {
    Set<PlayerData> statistics = new HashSet<>();
    // Traverse through the urls
    // for (String url : urls) {
      
    // }
    return statistics;
  }
}