import requests
import json
import os


def get_mod_path(mod_path):
    if not os.path.exists(mod_path):
        print("Default minecraft mods directory doesn't exists, have you download fabric yet ?"
              "If it's not the case here is the link: https://fabricmc.net/use/installer/"
              "If you have already download fabric, enter your minecraft mods directory")
        new_path = input("")
    else:
        print("Enter the path to your minecraft mod folder, if it's a default minecraft installation, just press enter")
        new_path = input("")
        if new_path == "":
            return mod_path
    if not os.path.exists(new_path):
        print("Can't detect a minecraft mod directory here, try again")
        return get_mod_path(mod_path)
    return new_path


def get_latest_version_info(mod_id):
    api_url = f"https://api.modrinth.com/v2/project/{mod_id}/version"
    params = {
        'loaders': json.dumps(["fabric"]),
        'game_versions': json.dumps([mc_version])
    }
    answer = requests.get(api_url, params=params)
    if answer.status_code == 200:
        versions = answer.json()
        for version in versions:
            if "fabric" in version['loaders'] and mc_version in version['game_versions']:
                return version
    return None


mc_version = "1.20.1"
mod_path = os.path.join(os.path.expanduser('~'), 'AppData', 'Roaming', '.minecraft', 'mods')
mod_names = ["architectury-api", "cloth-config", "distanthorizons", "indium", "iris", "lithium", "modmenu", "rei",
             "sodium"]
mod_urls = []
modrinth_names = []
file_download = [False] * len(mod_names)


mod_path = get_mod_path(mod_path)


for name in mod_names:
    infos = get_latest_version_info(name)
    if infos is not None:
        mod_urls.append(infos['files'][0]['url'])
        modrinth_names.append(infos['files'][0]['filename'])
    else:
        print(name)


# Loop through files in the directory
for filename in os.listdir(mod_path):
    if filename in modrinth_names:
        file_download[modrinth_names.index(filename)] = True
    else:
        os.remove(os.path.join(mod_path, filename))

if file_download == [True] * len(mod_names):
    print("You are already up to date !")
    exit()

failed = False
for i in range(len(mod_names)):
    if not file_download[i]:
        response = requests.get(mod_urls[i])
        if response.status_code == 200:
            with open(os.path.join(mod_path, modrinth_names[i]), 'wb') as f:
                f.write(response.content)
                print(f"Downloaded {modrinth_names[i]} successfully.")
        else:
            failed = True
            print(f"Failed to download {modrinth_names[i]}. Status code: {response.status_code}")

if not failed:
    print("You are now up to date !")
