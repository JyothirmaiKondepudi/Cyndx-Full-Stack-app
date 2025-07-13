FROM public.ecr.aws/lambda/python:3.12

WORKDIR /var/task

COPY requirements.txt ./
RUN pip install -r requirements.txt

COPY lambda_handler.py app.py config.py model.py errors.py ./

CMD ["lambda_handler.lambda_handler"]
