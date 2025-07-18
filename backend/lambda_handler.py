import boto3
from botocore.exceptions import ClientError
import json
import awsgi
from config import *
from app import create_app
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)


def get_secret():
    logger.info("🔑 Entering get_secret()")
    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name="us-east-1"
    )
    try:
        get_secret_value_response = client.get_secret_value(
            SecretId="flask/prod/postgres"
        )
        logger.info("🔑 AWS SDK call to Secrets Manager returned")
    except ClientError as e:
        # For a list of exceptions thrown, see
        # https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        raise e

    secret= json.loads(get_secret_value_response['SecretString'])
    logger.info("🔑 Parsed secret, returning to handler")
    return secret



def lambda_handler(event, context):
    logger.info("Starting handler")
    logger.info("Fetching secret…")
    secret = get_secret()
    logger.info("Secret fetched, connecting to DB…")
    username = secret["username"]
    password = secret["password"]
    host   = "forms.c2x2y28s29kk.us-east-1.rds.amazonaws.com"
    dbname = "postgres"
    logger.info("📡 About to connect to RDS at %s: 5432", host)

    ProductionConfig.SQLALCHEMY_DATABASE_URI = (
            f"postgresql+pg8000://{username}:{password}@{host}:5432/{dbname}"
        )
    _app_cache= create_app(ProductionConfig)
    logger.info("DB query done, about to return")
    return awsgi.response(_app_cache, event, context)