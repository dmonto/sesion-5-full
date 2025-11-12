
import click
from api.models import db, User
import datetime

def setup_commands(app):
    
    """ 
    This is an example command "insert-test-users" that you can run from the command line
    by typing: $ flask insert-test-users 5
    Note: 5 is the number of users to add
    """
    @app.cli.command("insert-test-users") # name of our command
    @click.argument("count") # argument of out command
    def insert_test_users(count):
        print("Creating test users")
        for x in range(1, int(count) + 1):
            user = User()
            user.email = "test_user" + str(x) + "@test.com"
            user.password = "123456"
            user.is_active = True
            db.session.add(user)
            db.session.commit()
            print("User: ", user.email, " created.")

        print("All test users created")

    @app.cli.command("revoke-unused-keys")
    def revoke_unused_keys():
        """
        Revokes API keys that have not been used in the last 90 days.
        Revoking means setting the api_key and last_used fields to null.
        """
        # Define el umbral de inactividad (90 días)
        n_days_threshold = 90
        d_threshold = datetime.datetime.utcnow() - datetime.timedelta(days=n_days_threshold)
        
        # Busca usuarios con claves API no utilizadas recientemente
        # Se incluyen también los usuarios con clave pero sin fecha de último uso (d_api_key_last_used is None)
        try:
            l_users_to_revoke = User.query.filter(
                User.s_api_key.isnot(None),
                (User.d_api_key_last_used < d_threshold) | (User.d_api_key_last_used.is_(None))
            ).all()

            if not l_users_to_revoke:
                print("No unused API keys to revoke.")
                return

            print(f"Found {len(l_users_to_revoke)} unused API keys to revoke...")

            # Revoca las claves
            for user in l_users_to_revoke:
                print(f"Revoking key for user: {user.email}")
                user.s_api_key = None
                user.d_api_key_last_used = None
            
            # Guarda los cambios en la base de datos
            db.session.commit()
            print("All unused keys have been revoked successfully.")

        except Exception as e:
            db.session.rollback()
            print(f"An error occurred: {e}")

    @app.cli.command("insert-test-data")
    def insert_test_data():
        pass
