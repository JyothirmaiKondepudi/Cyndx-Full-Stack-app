
class Config:
    SQLALCHEMY_TRACK_MODIFICATIONS = False

class DevelopmentConfig(Config):
    ENV='development'
    DEBUG = True
    SQLALCHEMY_DATABASE_URI = "postgresql+pg8000://postgres:@localhost:5432/forms"

class TestingConfig(Config):
    ENV="testing"
    TESTING = True
    SQLALCHEMY_DATABASE_URI = "sqlite:///:memory:"

class ProductionConfig(Config):
    ENV="production"
    SQLALCHEMY_DATABASE_URI= None

config_mapping = {
    "development":DevelopmentConfig,
    "testing":TestingConfig,
    "production":ProductionConfig
}