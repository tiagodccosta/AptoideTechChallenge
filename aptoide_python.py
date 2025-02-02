import requests
import base64

def search_apps():

    app_search_url = "https://ws75.aptoide.com/api/7/apps/get/store_name=apps/q=bWF4U2RrPTE5Jm1heFNjcmVlbj1ub3JtYWwmbWF4R2xlcz0yLjA/group_name=games/limit=10/offset=0/mature=false"

    try:
        response = requests.get(app_search_url)
        response.raise_for_status()

        data = response.json()

        print("=== Apps Found ===\n")
        for app in data.get("datalist", {}).get("list", []):
            print(f"App Name: {app.get('name')}")
            print(f"App ID: {app.get('id')}\n")

        q_parameter = "bWF4U2RrPTE5Jm1heFNjcmVlbj1ub3JtYWwmbWF4R2xlcz0yLjA="
        decoded_q_parameter = base64.b64decode(q_parameter).decode("utf-8")
        print(f"Device Specifications: {decoded_q_parameter}\n")

    except requests.exceptions.HTTPError as err:
        print(f"Error during request: {err}")
        return None
    
def get_app_details():

    app_details_url = "https://ws75.aptoide.com/api/7/app/get/store_name=apps/q=bWF4U2RrPTE5Jm1heFNjcmVlbj1ub3JtYWwmbWF4R2xlcz0yLjA/package_name=com.fun.lastwar.gp/language=pt_PT/"

    try:

        response = requests.get(app_details_url)
        response.raise_for_status()

        data = response.json()

        print("=== App Description ===\n")
        app_description = data.get("nodes", {}).get("meta", {}).get("data", {}).get("media", {}).get("description")
        print(app_description)
        print("\n")

    except requests.exceptions.HTTPError as err:
        print(f"Error during request: {err}")
        return None

def download_app():

    app_download_url = "https://aptoide-mmp.aptoide.com/api/v1/download/b2VtaWQ9VGVjaENoYWxsZW5nZVB5dGhvbiZwYWNrYWdlX25hbWU9Y29tLmZ1bi5sYXN0d2FyLmdwJnJlZGlyZWN0X3VybD1odHRwczovL3Bvb2wuYXBrLmFwdG9pZGUuY29tL2FwcHMvY29tLWZ1bi1sYXN0d2FyLWdwLTk5OTk5LTY2NjEyOTMwLWE3MThmOWZlMjE5OGM1Y2EyYzIwMmUwNDYzZTVkZDk1LmFwaw==?resolution=1080x1776&aptoide_uid=testchallenge"

    try:

        print("Download started...\n")

        response = requests.get(app_download_url)
        response.raise_for_status()

        with open("app.apk", "wb") as file:
            file.write(response.content)

        print("Download completed!\n")

    except requests.exceptions.HTTPError as err:
        print(f"Error during download: {err}")
        return None


def main():
    print("==== Aptoide Junior Challenge ====\n")
    print("Starting API requests...\n")

    search_apps()
    get_app_details()
    download_app()

if __name__ == "__main__":
    main()

