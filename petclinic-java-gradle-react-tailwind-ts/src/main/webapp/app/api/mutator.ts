import axios from 'axios';

export const customAxios = async <T>(url: string, options?: RequestInit): Promise<T> => {
  const response = await axios({
    url,
    method: options?.method || 'GET',
    data: options?.body ? JSON.parse(options.body as string) : undefined,
    headers: options?.headers as Record<string, string>,
  });
  return {
    data: response.data,
    status: response.status,
  } as T;
};
