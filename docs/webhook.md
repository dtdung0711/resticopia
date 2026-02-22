# Webhook Notifications

Resticopia can send webhook notifications when backups complete, allowing you to monitor backup status using external services like [Gatus](https://gatus.io/) or any service that accepts HTTP webhooks.

## Configuration

To configure a webhook for a repository:

1. Open **Repositories** tab
2. Select or create a repository
3. Scroll down to the **Webhook** section (collapsed by default)
4. Click to expand and configure:

| Setting | Description |
|---------|-------------|
| Webhook URL | The URL to call |
| Call webhook on successful backup | Send request when backup succeeds |
| Call webhook on failed backup | Send request when backup fails |
| Bearer token (optional) | Authentication token for services that require it |

### URL Template Variables

You can use placeholders in the webhook URL that will be replaced with actual values:

- `{success}` - `true` or `false` indicating backup result
- `{error}` - Error message (empty string if backup succeeded)
- `{duration}` - Backup duration (e.g., `15s`)

Example URL:
```
https://status.yourcompany.com/api/v1/endpoints/key/external?success={success}&error={error}&duration={duration}
```

## Request Format

Every webhook request is sent as an HTTP POST with a JSON body:

```json
{
  "success": true,
  "error": null,
  "duration": "15s",
  "device": "Pixel 7",
  "folderName": "Photos",
  "folderPath": "/storage/emulated/0/Photos"
}
```

### Body Fields

| Field | Description |
|-------|-------------|
| `success` | Boolean - `true` if backup succeeded, `false` if failed |
| `error` | String or null - Error message if backup failed |
| `duration` | String or null - Backup duration (e.g., "2m30s") |
| `device` | String - Device hostname from settings |
| `folderName` | String - Name of the folder being backed up |
| `folderPath` | String - Full path to the folder |

### Headers

- `Content-Type: application/json; charset=utf-8`
- `Authorization: Bearer <token>` (if bearer token is configured)


## Export/Import

Webhook configuration is included when you export/import app settings, making it easy to transfer your monitoring setup to another device.
