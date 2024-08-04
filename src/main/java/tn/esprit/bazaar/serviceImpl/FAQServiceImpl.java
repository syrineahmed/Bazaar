package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.FAQDto;
import tn.esprit.bazaar.entities.FAQ;
import tn.esprit.bazaar.entities.Product;
import tn.esprit.bazaar.repository.FAQRepository;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.service.FAQService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;
    private final PorductRepository porductRepository;



    //users can post a question about the product
    public FAQDto postFAQ(Long productId,FAQDto faqDto) {
        Optional<Product> optionalProduct = porductRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
}
