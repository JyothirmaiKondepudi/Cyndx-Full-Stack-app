import awsgi
import app
from config import ProductionConfig
import json
import boto3
from botocore.exceptions import ClientError

_app_cache    = None
_secret_cache = None

def get_secret():
    global _secret_cache
    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name="us-east-1"
    )

    try:
        get_secret_value_response = client.get_secret_value(
            SecretId="flask/prod/postgres"
        )
    except ClientError as e:
        # For a list of exceptions thrown, see
        # https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        raise e

    secret_cache = json.loads(get_secret_value_response['SecretString'])
    return secret_cache

def lambda_handler(event, context):
    global _app_cache
    secret = json.loads(get_secret())
    username = secret["username"]
    password = secret["password"]
    host   = "forms.c2x2y28s29kk.us-east-1.rds.amazonaws.com"
    dbname = "postgres"
    ProductionConfig.SQLALCHEMY_DATABASE_URI = (
            f"postgresql+pg8000://{username}:{password}@{host}:5432/{dbname}"
        )
    _app_cache= app.create_app(ProductionConfig)
    return awsgi.response(_app_cache, event, context)
