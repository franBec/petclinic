export { useAuthenticate } from 'app/__generated__/api/authentication/authentication';

export type { AuthenticationGetResponse, AuthenticationRequest } from 'app/__generated__/api/types';

export function unwrap<T>(response: unknown): T {
  return (response as { data: T }).data;
}
