import requests
from bs4 import BeautifulSoup

def fetch_html_from_url(url):
    response = requests.get(url)
    response.raise_for_status()
    html_content = response.text
    return html_content

url = ""
html_content = fetch_html_from_url(url)
soup = BeautifulSoup(html_content, 'html.parser')

# Example: Extract all <h1> tags
h1_tags = soup.find_all('h1')
for h1 in h1_tags:
    print(h1.text)



def parse_html(html):
   pass  # Parse the fetched HTML content and extract relevant information