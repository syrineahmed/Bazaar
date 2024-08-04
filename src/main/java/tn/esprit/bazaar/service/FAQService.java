package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.FAQDto;

public interface FAQService {
    FAQDto postFAQ(Long productId, FAQDto faqDto) ;

    }
