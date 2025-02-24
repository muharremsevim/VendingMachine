# Vending Machine Projesi

## Kurulum ve Çalıştırma

### Ön Gereksinimler
- Docker Desktop yüklü olmalı
- Git yüklü olmalı
- 8080 portu kullanılabilir olmalı

### Çalıştırma Adımları
1. Projeyi klonlayın:
   ```bash
   git clone <repository_url>
   cd VendingMachine
   ```

2. Docker containerları build edin ve başlatın:
   ```bash
   docker compose up --build
   ```

3. Tarayıcıdan erişin:
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - API Docs: http://localhost:8080/api-docs

### Önemli Notlar
- İlk çalıştırmada Maven bağımlılıklarının indirilmesi 5-10 dakika sürebilir
- Hata durumunda logları kontrol edin: `docker compose logs`
- Uygulamayı durdurmak için: `docker compose down`
- ADMIN kullanıcı için: username=admin password=admin123
