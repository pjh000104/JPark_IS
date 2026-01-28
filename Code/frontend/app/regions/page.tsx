import Link from "next/link";
import { getRegions } from "../lib/api"; 

export default async function Page() {
  const regions = await getRegions(); 

  return (
    <div>
      <h1>Regions</h1>
      <ul>
        {regions.map((region: string) => (
          <li key={region}>
            <Link
              href={`/regions/${encodeURIComponent(region)}`}
              className="hover:underline cursor-pointer"
            >
              {region}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}