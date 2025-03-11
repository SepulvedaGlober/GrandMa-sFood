package project.grandmasfood.infrastructure.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.grandmasfood.domain.api.IClientServicePort;
import project.grandmasfood.domain.api.IProductServicePort;
import project.grandmasfood.domain.spi.IClientPersistencePort;
import project.grandmasfood.domain.spi.IProductPersistencePort;
import project.grandmasfood.domain.usecase.ClientUseCase;
import project.grandmasfood.domain.usecase.ProductUseCase;
import project.grandmasfood.infrastructure.jpa.adapter.ClientJpaAdapter;
import project.grandmasfood.infrastructure.jpa.adapter.ProductJpaAdapter;
import project.grandmasfood.infrastructure.jpa.mapper.client.IClientEntityMapper;
import project.grandmasfood.infrastructure.jpa.mapper.product.IProductEntityMapper;
import project.grandmasfood.infrastructure.jpa.repository.IClientRepository;
import project.grandmasfood.infrastructure.jpa.repository.IProductRepository;

@RequiredArgsConstructor
@Configuration
public class BeanConfiguration {

    //Product
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    //Client
    private final IClientRepository clientRepository;
    private final IClientEntityMapper clientEntityMapper;

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(productRepository, productEntityMapper);
    }

    @Bean
    public IClientServicePort clientServicePort() {
        return new ClientUseCase(clientPersistencePort());
    }

    @Bean
    public IClientPersistencePort clientPersistencePort() {
        return new ClientJpaAdapter(clientRepository, clientEntityMapper);
    }


}
